package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusPromotion;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.PromotionRepository;
import vn.insee.service.PromotionServiceCommon;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService extends PromotionServiceCommon {


    public List<PromotionEntity> findPromotion(int type, UserEntity userEntity) {
        List<PromotionEntity> promotionEntities = repository.findAllByOrderByUpdatedTimeDesc();
        return promotionEntities.stream().filter(promotion -> promotion.getType() == type)
                .filter(promotion -> promotion.getStatus() == StatusPromotion.APPROVED)
                .filter(promotion -> {
                    if (promotion.getCityIds() != null) {
                        return promotion.getCityIds().contains(userEntity.getCityId());
                    }
                    return true;
                })
                .filter(promotion -> {
                    if (promotion.getDistrictIds() != null) {
                        return promotion.getDistrictIds().contains(userEntity.getDistrictId());
                    }
                    return true;
                }).collect(Collectors.toList());
    }

    public PromotionEntity get(int id) {
        return repository.getOne(id);
    }
}
