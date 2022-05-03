package vn.insee.retailer.webhook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.retailer.bot.LightingSession;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.message.Before5MinMessage;
import vn.insee.retailer.bot.message.DoneFormMessage;
import vn.insee.retailer.bot.message.NotFoundPromotionMessage;
import vn.insee.retailer.bot.message.Time2StartGameMessage;
import vn.insee.retailer.bot.script.LightingQuizScript;
import vn.insee.retailer.controller.dto.TopicDTO;
import vn.insee.retailer.service.LightingQuizFormService;
import vn.insee.retailer.service.LightingQuizPromotionService;
import vn.insee.retailer.service.PostService;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.webhook.zalo.UserSendMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private PostService postService;

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
                LightingQuizPromotionEntity promotionEntity = getPromotion(userEntity);
                if (promotionEntity == null) {
                    NotFoundPromotionMessage notFoundLQPromotionMessage = new NotFoundPromotionMessage(user);
                    notFoundLQPromotionMessage.send();
                    return true;
                }

                long currentTime = System.currentTimeMillis();
                TopicDTO topicUpComing = getTopic(promotionEntity);
                long startTimeUpComing = topicUpComing.getTimeStart();
                if (startTimeUpComing <= currentTime) {
                    LightingQuizFormEntity lightingQuizFormEntity = findForm(user.getUid(),
                            promotionEntity.getId(),
                            topicUpComing.getId());
                    if (lightingQuizFormEntity != null) {
                        new DoneFormMessage(user).send();
                    }else {
                        //start topic
                        new LightingQuizScript(user).start(promotionEntity, topicUpComing);
                    }
                    return true;
                }

                PostEntity postEntity = postService.findPost(promotionEntity.getId(), userEntity);
                //sap bat dau
                if (startTimeUpComing - currentTime <= 5 * 60 * 1000) {
                    Before5MinMessage before5MinMessage = new Before5MinMessage(user, topicUpComing.getTitle(),
                            postEntity != null ? postEntity.getId() : 0,
                            topicUpComing.getTimeStart());
                    before5MinMessage.send();
                }else {
                    Time2StartGameMessage time2StartGameMessage = new Time2StartGameMessage(user, topicUpComing.getTitle(),
                            postEntity != null ? postEntity.getId() : 0, startTimeUpComing);
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

    public void forceStart(int promotionId, String topicId) throws Exception {
        LightingQuizPromotionEntity promotionEntity = lightingQuizPromotionService.get(promotionId);
        if (promotionEntity == null) {
            throw new Exception("promotion is not exit: " + promotionId);
        }
        List<TopicDTO> listTopicDTO = lightingQuizPromotionService.getListTopicDTO(promotionEntity);
        if (listTopicDTO == null) {
            throw new Exception("topics is null " + promotionId);
        }
        Optional<TopicDTO> opTopicDTO = listTopicDTO.stream().filter(topic -> topic.getId().equals(topicId)).findFirst();
        if (!opTopicDTO.isPresent()) {
            throw new Exception("topic is not exits " + topicId);
        }

        List<Integer> cityIds = promotionEntity.getCityIds();
        List<Integer> districtIds = promotionEntity.getDistrictIds();

        List<UserEntity> userEntities = userService.findBy(cityIds, districtIds, StatusUser.APPROVED);
        if (userEntities != null) {
            userEntities.stream().forEach(userEntity -> {
                start(promotionEntity, opTopicDTO.get(), userEntity);
            });
        }
    }

    private void start(LightingQuizPromotionEntity promotionEntity, TopicDTO topic, UserEntity userEntity) {
        try {
            User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
            new LightingQuizScript(user).start(promotionEntity, topic);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private LightingQuizPromotionEntity getPromotion(UserEntity userEntity) {
        try{
            return lightingQuizPromotionService.getUpComingPromotion(userEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private TopicDTO getTopic(LightingQuizPromotionEntity promotionEntity) {
        try {
            return lightingQuizPromotionService.getTopicUpComing(promotionEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private LightingQuizFormEntity findForm(int userId, int promotionId, String topicId) {
        try {
            return lightingQuizFormService.find(userId, promotionId, topicId);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
