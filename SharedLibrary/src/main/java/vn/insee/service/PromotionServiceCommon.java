package vn.insee.service;

import org.springframework.beans.factory.annotation.Autowired;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.PromotionRepository;

public abstract class PromotionServiceCommon {

    @Autowired
    protected PromotionRepository repository;

    public boolean isOpen(PromotionEntity promotionEntity) {
        long currentTime = System.currentTimeMillis();
        return currentTime >= promotionEntity.getTimeStart() && currentTime <= promotionEntity.getTimeEnd();
    }

    public boolean isOpenFor(PromotionEntity promotionEntity, UserEntity userEntity) {
        if (isOpen(promotionEntity)) {
            if (promotionEntity.getDistrictIds() != null) {
                return promotionEntity.getDistrictIds().contains(userEntity.getDistrictId());
            }
            if (promotionEntity.getCityIds() != null) {
                return promotionEntity.getCityIds().contains(userEntity.getCityId());
            }
            return true;
        }
        return false;
    }
}
