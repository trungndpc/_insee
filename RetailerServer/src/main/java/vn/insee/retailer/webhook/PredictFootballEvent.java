package vn.insee.retailer.webhook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;
import vn.insee.retailer.service.PredictFootballPromotionService;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;

@Component
public class PredictFootballEvent extends ZaloEvent{
    private static final Logger LOGGER = LogManager.getLogger(PredictFootballEvent.class);

    @Autowired
    private PredictFootballPromotionService promotionService;

    @Override
    public boolean process(ZaloWebhookMessage zaloWebhookMessage) {
        return false;
    }

    private PredictFootballPromotionEntity getPromotion(UserEntity userEntity) {
        try{
            return promotionService.findLatest(userEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }


}
