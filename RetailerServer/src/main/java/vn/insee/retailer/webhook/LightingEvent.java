package vn.insee.retailer.webhook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.retailer.bot.LightingSession;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.message.Before5MinMessage;
import vn.insee.retailer.bot.message.NotFoundLQPromotionMessage;
import vn.insee.retailer.bot.message.Time2StartGameMessage;
import vn.insee.retailer.bot.script.LightingQuizScript;
import vn.insee.retailer.common.UserStatus;
import vn.insee.retailer.controller.dto.TopicDTO;
import vn.insee.retailer.service.LightingQuizFormService;
import vn.insee.retailer.service.LightingQuizPromotionService;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.webhook.zalo.FollowZaloWebhookMessage;
import vn.insee.retailer.webhook.zalo.UserSendMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;

@Component
public class LightingEvent extends ZaloEvent{

    private static final Logger LOGGER = LogManager.getLogger(LightingEvent.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WebhookSessionManager webhookSessionManager;

    @Autowired
    private LightingQuizPromotionService lightingQuizPromotionService;

    @Autowired
    private LightingQuizFormService lightingQuizFormService;


    @Override
    public boolean process(ZaloWebhookMessage zSendMessage) {
        try{
            UserSendMessage userSendMessage = (UserSendMessage) zSendMessage;
            String text = userSendMessage.message.text;
            UserEntity userEntity = userService.findByZaloId(userSendMessage.userIdByApp);
            Object currentSession = webhookSessionManager.getCurrentSession(userEntity.getId());
            User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
            if (currentSession != null && currentSession instanceof LightingSession) {
                new LightingQuizScript(user).process(userSendMessage);
                return true;
            }

            if (text.equals("#nhanh_nhu_chop")) {

                LightingQuizPromotionEntity promotionEntity = lightingQuizPromotionService.getUpComing(userEntity.getCityId());
                if (promotionEntity == null) {
                    NotFoundLQPromotionMessage notFoundLQPromotionMessage = new NotFoundLQPromotionMessage(user);
                    notFoundLQPromotionMessage.send();
                    return true;
                }

                long currentTime = System.currentTimeMillis();
                TopicDTO topicUpComing = lightingQuizPromotionService.getTopicUpComing(promotionEntity);
                long startTimeUpComing = topicUpComing.getTimeStart();
                if (startTimeUpComing - currentTime <= 0) {
                    //todo
                    new LightingQuizScript(user).start(promotionEntity, topicUpComing);
                    return true;
                }

                if (startTimeUpComing - currentTime <= 5 * 60 * 1000) {
                    Before5MinMessage before5MinMessage = new Before5MinMessage(user, topicUpComing.getTitle(), promotionEntity.getId());
                    before5MinMessage.send();
                }else {
                    Time2StartGameMessage time2StartGameMessage = new Time2StartGameMessage(user, topicUpComing.getTitle(),
                            promotionEntity.getId(), startTimeUpComing);
                    time2StartGameMessage.send();
                }
                return true;
            }

            return true;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
