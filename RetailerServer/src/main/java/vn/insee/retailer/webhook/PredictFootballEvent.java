package vn.insee.retailer.webhook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;
import vn.insee.retailer.bot.LightingSession;
import vn.insee.retailer.bot.PredictFootballSession;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.message.Before5MinMessage;
import vn.insee.retailer.bot.message.DoneFormMessage;
import vn.insee.retailer.bot.message.NotFoundPromotionMessage;
import vn.insee.retailer.bot.message.Time2StartGameMessage;
import vn.insee.retailer.bot.script.FootballScript;
import vn.insee.retailer.bot.script.LightingQuizScript;
import vn.insee.retailer.controller.dto.TopicDTO;
import vn.insee.retailer.service.PredictFootballPromotionService;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.webhook.zalo.UserSendMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;

import java.util.List;
import java.util.Optional;

@Component
public class PredictFootballEvent extends ZaloEvent{
    private static final Logger LOGGER = LogManager.getLogger(PredictFootballEvent.class);

    @Autowired
    private PredictFootballPromotionService promotionService;

    @Autowired
    private UserService userService;

    @Autowired
    private WebhookSessionManager webhookSessionManager;

    @Override
    public boolean process(ZaloWebhookMessage zSendMessage) {
        try{
            UserSendMessage userSendMessage = (UserSendMessage) zSendMessage;
            String text = userSendMessage.message.text;
            UserEntity userEntity = userService.findByZaloId(userSendMessage.userIdByApp);
            Object currentSession = webhookSessionManager.getCurrentSession(userEntity.getId());
            User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
            if (currentSession != null && currentSession instanceof PredictFootballSession) {
                new FootballScript(user).process(userSendMessage);
                return true;
            }

            if (text.equals("#du_doan_bong_da")) {
                PredictFootballPromotionEntity promotionEntity = getPromotion(userEntity);
                if (promotionEntity == null) {
                    NotFoundPromotionMessage notFoundLQPromotionMessage = new NotFoundPromotionMessage(user);
                    notFoundLQPromotionMessage.send();
                    return true;
                }
                start(promotionEntity, userEntity);
                return true;
            }

        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }


    public void start(PredictFootballPromotionEntity promotionEntity, UserEntity userEntity) {
        try {
            User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
            new FootballScript(user).start(promotionEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
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
