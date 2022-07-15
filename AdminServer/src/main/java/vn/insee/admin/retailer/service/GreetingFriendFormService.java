package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.message.ApprovedStockPromotionMessage;
import vn.insee.admin.retailer.message.RejectedStockFormMessage;
import vn.insee.admin.retailer.message.User;
import vn.insee.common.status.StatusForm;
import vn.insee.common.status.StatusStockForm;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.jpa.entity.promotion.GreetingFriendPromotionEntity;
import vn.insee.jpa.repository.GreetingFriendFormRepository;
import vn.insee.jpa.specification.GreetingFriendFormSpecification;
import vn.insee.service.GreetingFriendFormServiceCommon;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GreetingFriendFormService extends GreetingFriendFormServiceCommon {

    @Autowired
    private GreetingFriendFormRepository repository;

    @Autowired
    private GreetingFriendFormSpecification specification;

    @Autowired
    private UserService userService;

    public Page<GreetingFriendFormEntity> findByPromotionId(int promotionId, Integer status, Integer city,
                                              String search, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "created_time"));
        Specification<GreetingFriendFormEntity> specs = Specification.where(null);
        specs = specs.and(specification.isPromotion(promotionId));
        if (status != null) {
            specs = specs.and(specification.isStatus(status));
        }
        List<GreetingFriendFormEntity> list = repository.findAll(specs);
        if (list == null || list.isEmpty()) {
            return Page.empty();
        }

        if (city != null || search != null) {
            list = list.stream().filter(formEntity -> {
                UserEntity userEntity = userService.findById(formEntity.getUserId());
                boolean is = false;
                if (city != null) {
                    is = userEntity.getCityId() == city;
                }
                if (search != null) {
                    is = userEntity.getName().contains(search) || userEntity.getPhone().contains(search);
                }
                return is;
            }).collect(Collectors.toList());
        }
        list = list.stream().sorted((t1, t2) -> t2.getCreatedTime().compareTo(t1.getCreatedTime())).collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    public void updateStatus(int id, int status, String note) throws Exception {
        GreetingFriendFormEntity entity = repository.getOne(id);
        if (entity == null) {
            throw new Exception("GreetingFriendFormEntity is not exits id: " + id);
        }

        entity.setStatus(status);
        UserEntity userEntity = userService.findById(entity.getUserId());
        User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());

        if (status == StatusStockForm.APPROVED) {
            ApprovedStockPromotionMessage approvedStockPromotionMessage = new ApprovedStockPromotionMessage(user, userEntity.getName());
            approvedStockPromotionMessage.send();
        }

        if (status == StatusStockForm.REJECTED) {
            entity.setNote(note);
            RejectedStockFormMessage rejectedStockFormMessage = new RejectedStockFormMessage(user, note);
            rejectedStockFormMessage.send();
        }

        repository.saveAndFlush(entity);
    }


}
