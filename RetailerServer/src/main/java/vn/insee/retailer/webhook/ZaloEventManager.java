package vn.insee.retailer.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.retailer.bot.LightingSession;
import vn.insee.retailer.bot.PredictFootballSession;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.script.LightingQuizScript;
import vn.insee.retailer.service.LightingQuizPromotionService;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.webhook.zalo.FollowZaloWebhookMessage;
import vn.insee.retailer.webhook.zalo.UserReceivedZNSMessage;
import vn.insee.retailer.webhook.zalo.UserSendMessage;

import java.util.*;

@Service
public class ZaloEventManager {
    private static final Logger LOGGER = LogManager.getLogger(ZaloEventManager.class);

    @Autowired
    private WebhookSessionManager webhookSessionManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FollowZaloEvent followZaloEvent;

    @Autowired
    private LightingEvent lightingEvent;

    @Autowired
    private UserReceivedZNSEvent userReceivedZNSEvent;

    @Autowired
    private UserService userService;

    @Autowired
    private PredictFootballEvent predictFootballEvent;

    public void doing(JSONObject zaloMsg) throws Exception {
        LOGGER.info(zaloMsg);
        String eventName = zaloMsg.optString("event_name", "");
        switch (eventName) {
            case "follow":
                FollowZaloWebhookMessage userFollow = objectMapper.readValue(zaloMsg.toString(), FollowZaloWebhookMessage.class);
                followZaloEvent.process(userFollow);
                return;
            case "user_received_message":
                UserReceivedZNSMessage receivedZNSMessage = objectMapper.readValue(zaloMsg.toString(), UserReceivedZNSMessage.class);
                userReceivedZNSEvent.process(receivedZNSMessage);
                return;
            case "user_send_text":
                long userByAppId = zaloMsg.getLong("user_id_by_app");
                UserEntity userEntity = userService.findByZaloId(String.valueOf(userByAppId));
                if (userEntity == null) {
                    throw new Exception("not found user user_id_by_app: " + userByAppId + ", zaloMsg: " + zaloMsg);
                }
                UserSendMessage userSendText = objectMapper.readValue(zaloMsg.toString(), UserSendMessage.class);
                String text = userSendText.message.text;
                Object session = webhookSessionManager.getCurrentSession(userEntity.getId());

                if (session != null) {
                    LOGGER.error("session: " + session.getClass());
                }

                if (text.equals("#nhanh_nhu_chop") || session instanceof LightingSession) {
                    lightingEvent.process(userSendText);
                    return;
                }

                if (text.equals("#du_doan_bong_da") || session instanceof PredictFootballSession) {
                    predictFootballEvent.process(userSendText);
                    return;
                }
                break;
        }
    }

}
