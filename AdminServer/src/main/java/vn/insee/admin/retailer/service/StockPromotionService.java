package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusPromotion;
import vn.insee.jpa.entity.promotion.StockPromotionEntity;
import vn.insee.jpa.repository.StockPromotionRepository;

@Service
public class StockPromotionService {

    @Autowired
    private StockPromotionRepository stockPromotionRepository;

    public StockPromotionEntity create(StockPromotionEntity entity) {
        entity.setStatus(StatusPromotion.INIT);
        return stockPromotionRepository.saveAndFlush(entity);
    }

    public StockPromotionEntity get(int id) {
        return stockPromotionRepository.getOne(id);
    }

}
