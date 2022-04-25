package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;
import vn.insee.jpa.repository.PredictFootballPromotionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PredictFootballPromotionService extends PromotionService{

    @Autowired
    private PredictFootballPromotionRepository repository;

    public PredictFootballPromotionEntity findLatest(UserEntity user) {
        long currentTime = System.currentTimeMillis();
        List<PromotionEntity> promotionEntities = findPromotion(TypePromotion.PREDICT_FOOTBALL, user);
        if (promotionEntities == null || promotionEntities.isEmpty()) {
            return null;
        }
        promotionEntities = promotionEntities.stream().filter(pre -> pre.getTimeStart() <= currentTime && pre.getTimeEnd() >= currentTime)
                .collect(Collectors.toList());
        if (promotionEntities == null || promotionEntities.isEmpty()) {
            return null;
        }
        return (PredictFootballPromotionEntity) promotionEntities.get(0);
    }
}
