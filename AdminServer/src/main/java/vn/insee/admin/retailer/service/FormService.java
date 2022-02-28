package vn.insee.admin.retailer.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.message.ApprovedStockPromotionMessage;
import vn.insee.admin.retailer.message.RejectedStockFormMessage;
import vn.insee.admin.retailer.message.User;
import vn.insee.common.status.StatusForm;
import vn.insee.common.status.StatusStockForm;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.jpa.metric.FormCityMetric;
import vn.insee.jpa.metric.FormDateMetric;
import vn.insee.jpa.metric.FormPromotionMetric;
import vn.insee.jpa.metric.UserDataMetric;
import vn.insee.jpa.repository.FormRepository;
import vn.insee.jpa.repository.StockFormRepository;
import vn.insee.jpa.specification.FormSpecification;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormService {
    private static final Logger LOGGER = LogManager.getLogger(FormService.class);

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StockFormRepository stockFormRepository;

    @Autowired
    private FormSpecification formSpecification;

    public Page<FormEntity> findByPromotionId(int promotionId, Integer status, Integer city,
                                              String search, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "created_time"));
        Specification<FormEntity> specs =  Specification.where(null);
        specs = specs.and(formSpecification.isPromotion(promotionId));
        if (status != null) {
            specs = specs.and(formSpecification.isStatus(status));
        }
        List<FormEntity> all = formRepository.findAll(specs);
        if (all != null && !all.isEmpty()) {
            if (city != null || search != null ) {
                all = all.stream().filter(formEntity -> {
                    UserEntity userEntity = userService.findById(formEntity.getUserId());
                    boolean is = false;
                    if (city != null) {
                        is = userEntity.getCityId() == city;
                    }
                    if (search != null) {
                        is = userEntity.getName().contains(search) || userEntity.getPhone().contains(search);
                    }
                    return is;
                }).sorted((t1, t2) -> t1.getUpdatedTime().compareTo(t2.getUpdatedTime()))
                        .collect(Collectors.toList());
            }

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), all.size());
            return new PageImpl<>(all.subList(start, end), pageable, all.size());
        }
        return Page.empty();
    }

    public List<StockFormEntity> findByPromotionId(int promotionId) {
        return stockFormRepository.findByPromotionId(promotionId);
    }

    public Page<FormEntity> findByPromotions(List<Integer> promotionIds, Integer status, Integer city,
                                              String search, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<FormEntity> specs =  Specification.where(null);
        specs = specs.and(formSpecification.inPromotions(promotionIds));
        if (status != null) {
            specs = specs.and(formSpecification.isStatus(status));
        }
        List<FormEntity> all = formRepository.findAll(specs);
        if (all != null && !all.isEmpty()) {
            if (city != null || search != null ) {
                all = all.stream().filter(formEntity -> {
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
            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), all.size());
            return new PageImpl<>(all.subList(start, end), pageable, all.size());
        }
        return Page.empty();
    }

    public FormEntity getById(int id) {
        FormEntity one = formRepository.getOne(id);
        if (one.getType() == TypePromotion.STOCK_PROMOTION_TYPE) {
            StockFormEntity stockFormEntity = stockFormRepository.getOne(one.getId());
            return stockFormEntity;
        }
        return one;
    }

    public void updateStatus(int id, int status, String note) throws Exception {
        StockFormEntity stockFormEntity = stockFormRepository.getOne(id);
        if (stockFormEntity == null) {
            throw new Exception("stockFormEntity is not exits id: " + id);
        }
        stockFormEntity.setStatus(status);
        UserEntity userEntity = userService.findById(stockFormEntity.getUserId());
        User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());

        if (status == StatusStockForm.APPROVED) {
            ApprovedStockPromotionMessage approvedStockPromotionMessage = new ApprovedStockPromotionMessage(user, userEntity.getName());
            approvedStockPromotionMessage.send();
        }

        if (status == StatusStockForm.REJECTED) {
            stockFormEntity.setNote(note);
            RejectedStockFormMessage rejectedStockFormMessage = new RejectedStockFormMessage(user, note);
            rejectedStockFormMessage.send();
        }
        stockFormRepository.saveAndFlush(stockFormEntity);
    }

    public void update(StockFormEntity stockFormEntity) {
        stockFormRepository.saveAndFlush(stockFormEntity);
    }

    public FormEntity addGift(int formId, List<Integer> giftIds) {
        FormEntity formEntity = formRepository.getOne(formId);
        formEntity.setGifts(giftIds);
        formEntity.setStatus(StatusForm.SENT_GIFT);
        return formRepository.saveAndFlush(formEntity);
    }

    public long count(List<Integer> promotionIds, Integer status) {
        Specification<FormEntity> specs =  Specification.where(null);
        if (promotionIds != null) {
            specs = specs.and(formSpecification.inPromotions(promotionIds));
        }

        if (status != null) {
            specs = specs.and(formSpecification.isStatus(status));
        }
        return formRepository.count(specs);
    }

    public List<FormDateMetric> statisticFormByDate(List<Integer> promotionIds) {
        return formRepository.statisticFormByDate(promotionIds);
    }

    public List<FormCityMetric> statisticFormByCity(List<Integer> promotionIds) {
        return formRepository.statisticFormByCity(promotionIds);
    }

    public List<FormPromotionMetric> statisticFormByPromotion() {
        return formRepository.statisticFormByPromotion();
    }

}
