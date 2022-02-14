package vn.insee.admin.retailer.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.message.ApprovedStockPromotionMessage;
import vn.insee.admin.retailer.message.User;
import vn.insee.common.status.StatusStockForm;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.jpa.repository.FormRepository;
import vn.insee.jpa.repository.StockFormRepository;

@Service
public class StockFormService {
    private static final Logger LOGGER = LogManager.getLogger(StockFormService.class);

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StockFormRepository stockFormRepository;

    public Page<FormEntity> findByPromotionId(int promotionId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return formRepository.findByPromotionId(promotionId, pageable);
    }

    public long countByPromotionAndStatus(int promotionId, int status) {
        return formRepository.countByPromotionIdAndStatus(promotionId, status);
    }

    public long countByPromotion(int promotion) {
        return formRepository.countByPromotionId(promotion);
    }

    public FormEntity getById(int id) {
        FormEntity one = formRepository.getOne(id);
        if (one.getType() == TypePromotion.STOCK_PROMOTION_TYPE) {
            StockFormEntity stockFormEntity = stockFormRepository.getOne(one.getId());
            return stockFormEntity;
        }
        return one;
    }

    public void updateStatus(int id, int status) throws Exception {
        StockFormEntity stockFormEntity = stockFormRepository.getOne(id);
        if (stockFormEntity == null) {
            throw new Exception("stockFormEntity is not exits id: " + id);
        }
        stockFormEntity.setStatus(status);
        if (status == StatusStockForm.APPROVED) {
            UserEntity userEntity = userService.findById(stockFormEntity.getUserId());
            User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
            ApprovedStockPromotionMessage approvedStockPromotionMessage = new ApprovedStockPromotionMessage(user, userEntity.getName());
            approvedStockPromotionMessage.send();
        }
        stockFormRepository.saveAndFlush(stockFormEntity);
    }
}
