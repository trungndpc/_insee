package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusPromotion;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.GreetingFriendPromotionEntity;
import vn.insee.jpa.repository.GreetingFriendPromotionRepository;
import vn.insee.service.GreetingFriendPromotionServiceCommon;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GreetingFriendPromotionService extends GreetingFriendPromotionServiceCommon {

    public GreetingFriendPromotionEntity create(GreetingFriendPromotionEntity entity) {
        return repository.saveAndFlush(entity);
    }

}
