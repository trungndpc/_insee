package vn.insee.retailer.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.common.UserStatus;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.webhook.zalo.FollowZaloWebhookMessage;

@Component
public class FollowZaloEvent extends ZaloEvent{

    private static final Logger LOGGER = LogManager.getLogger(FollowZaloEvent.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean isAccepted(JSONObject json) {
        return json.optString("event_name", "").equals("follow");
    }

    @Override
    public boolean process(JSONObject json) {
        try{
            FollowZaloWebhookMessage followZaloMsg = objectMapper.readValue(json.toString(), FollowZaloWebhookMessage.class);
            UserEntity userEntity = userService.findByZaloId(followZaloMsg.userIdByApp);
            if (userEntity == null) {
                userEntity = new UserEntity();
                userEntity.setStatus(UserStatus.WAIT_COMPLETE_PROFILE);
            }
            userEntity.setZaloId(String.valueOf(followZaloMsg.userIdByApp));
            userEntity.setFollowerId(followZaloMsg.follower.id);
            userService.saveOrUpdate(userEntity);
            return true;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
