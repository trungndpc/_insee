package vn.insee.service;

import org.springframework.beans.factory.annotation.Autowired;
import vn.insee.common.status.StatusPromotion;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.GreetingFriendPromotionEntity;
import vn.insee.jpa.repository.GreetingFriendPromotionRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GreetingFriendPromotionServiceCommon {

    @Autowired
    protected GreetingFriendPromotionRepository repository;

    public List<GreetingFriendPromotionEntity> findActive(UserEntity userEntity) {
        List<GreetingFriendPromotionEntity> list =
                repository.findByStatus(StatusPromotion.APPROVED);

        if (list == null) {
            return null;
        }

        return list.stream()
                .filter(p -> {
                    if (p.getCityIds() != null) {
                        return p.getCityIds().contains(userEntity.getCityId());
                    }
                    return true;
                })
                .filter(p -> {
                    if (p.getDistrictIds() != null) {
                        return p.getDistrictIds().contains(userEntity.getDistrictId());
                    }
                    return true;
                })
                .filter(p -> {
                    long currentTime = System.currentTimeMillis();
                    return p.getTimeStart() <= currentTime && p.getTimeEnd() >= currentTime;
                }).collect(Collectors.toList());
    }





}
