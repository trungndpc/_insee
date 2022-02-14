package vn.insee.retailer.webhook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.controller.AuthenController;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.webhook.zalo.FollowZaloWebhookMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;

@Component
public class FollowZaloEvent extends ZaloEvent{

    private static final Logger LOGGER = LogManager.getLogger(FollowZaloEvent.class);

    @Autowired
    private UserService userService;



    @Override
    public boolean process(ZaloWebhookMessage zaloWebhookMessage) {
        try{
            FollowZaloWebhookMessage followZaloMsg = (FollowZaloWebhookMessage) zaloWebhookMessage;
            UserEntity userEntity = userService.findByZaloId(followZaloMsg.userIdByApp);
            if (userEntity == null) {
                userEntity = new UserEntity();
                userEntity.setStatus(StatusUser.WAIT_COMPLETE_PROFILE);
            }
            userEntity.setZaloId(String.valueOf(followZaloMsg.userIdByApp));
            userEntity.setFollowerId(followZaloMsg.follower.id);
            userEntity = userService.saveOrUpdate(userEntity);
            //flow register async
            AuthenController.MAP_FOLLOWER.put(String.valueOf(followZaloMsg.userIdByApp), userEntity.getId());
            return true;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
