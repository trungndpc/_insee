package vn.insee.retailer.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.bot.LightingSession;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.script.LightingQuizScript;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.webhook.zalo.FollowZaloWebhookMessage;
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
    private UserService userService;

    public void doing(JSONObject zaloMsg) throws Exception {
        long userByAppId = zaloMsg.getLong("user_id_by_app");
        String eventName = zaloMsg.optString("event_name", "");

        UserEntity userEntity = userService.findByZaloId(userByAppId);
        if (userEntity == null) {
            throw new Exception("not found user user_id_by_app: " + userByAppId + ", zaloMsg: " + zaloMsg);
        }
        User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());

        switch (eventName) {
            case "follow":
                FollowZaloWebhookMessage userFollow = objectMapper.readValue(zaloMsg.toString(), FollowZaloWebhookMessage.class);
                followZaloEvent.process(userFollow);
                return;
            case "user_send_text":
                UserSendMessage userSendText = objectMapper.readValue(zaloMsg.toString(), UserSendMessage.class);
                String text = userSendText.message.text;
                Object session = webhookSessionManager.getCurrentSession(user.getUid());

                if (text.equals("#nhanh_nhu_chop") || session instanceof LightingSession) {
                        new LightingQuizScript(user).process(userSendText);
                    return;
                }
                break;
        }
    }

}
