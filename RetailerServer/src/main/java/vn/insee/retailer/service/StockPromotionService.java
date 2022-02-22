package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusPromotion;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.PromotionRepository;
import vn.insee.jpa.specification.PromotionSpecification;

import java.util.List;
import java.util.Optional;

@Service
public class StockPromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionSpecification promotionSpecification;

    public  PromotionEntity find(UserEntity userEntity) {
        long currentTime = System.currentTimeMillis();
        List<PromotionEntity> promotionEntityList = promotionRepository.findByTypeAndStatus(TypePromotion.STOCK_PROMOTION_TYPE,
                StatusPromotion.APPROVED);
        Optional<PromotionEntity> first = promotionEntityList.stream().filter(e -> {
                    if (e.getDistrictIds() != null) {
                        return e.getDistrictIds().contains(userEntity.getDistrictId());
                    }
                    if (e.getCityIds() != null) {
                        return e.getCityIds().contains(userEntity.getCityId());
                    }
                    return true;
                }).filter(e -> e.getTimeStart() <= currentTime)
                .filter(e -> currentTime <= e.getTimeEnd())
                .findFirst();
        if (!first.isPresent()) {
            return null;
        }
        return first.get();
    }
}
