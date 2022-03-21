package vn.insee.retailer.webhook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.controller.AuthenController;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.util.StringUtils;
import vn.insee.retailer.webhook.zalo.FollowZaloWebhookMessage;
import vn.insee.retailer.webhook.zalo.UserReceivedZNSMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;

@Component
public class UserReceivedZNSEvent extends ZaloEvent{
    private static final Logger LOGGER = LogManager.getLogger(UserReceivedZNSEvent.class);

    @Autowired
    private UserService userService;


    @Override
    public boolean process(ZaloWebhookMessage zaloWebhookMessage) {
        try{
            UserReceivedZNSMessage userReceivedZNSMessage = ((UserReceivedZNSMessage)zaloWebhookMessage);
            UserReceivedZNSMessage.Content message = userReceivedZNSMessage.getMessage();
            String phoneTracking = message.getTracking_id();
            LOGGER.info("phoneTracking: " + phoneTracking);
            if (phoneTracking != null && !StringUtils.isEmpty(phoneTracking)) {
                UserEntity userEntity = userService.findByPhone(phoneTracking);
                LOGGER.error(userEntity);
                if (userEntity != null) {
                    if (userEntity.getStatus() != StatusUser.WAITING_ACTIVE) {
                        throw new Exception("status user is not waiting active");
                    }
                    LOGGER.error("update: setFollowerId: " + userReceivedZNSMessage.recipient.id);
                    userEntity.setFollowerId(userReceivedZNSMessage.recipient.id);
                    userEntity.setZaloId(String.valueOf(zaloWebhookMessage.userIdByApp));
                    userService.saveOrUpdate(userEntity);
                }
            }
            return true;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
