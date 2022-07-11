package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.common.UserStatus;
import vn.insee.admin.retailer.service.GreetingFriendFormService;
import vn.insee.admin.retailer.service.GreetingFriendPromotionService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.GreetingFriendPromotionEntity;

import java.util.List;

@RestController
@RequestMapping("/int")
public class InternalController {
    private static final Logger LOGGER = LogManager.getLogger(InternalController.class);

    @Autowired
    private UserService userService;


    @Autowired
    private GreetingFriendPromotionService greetingFriendPromotionService;

    @Autowired
    private GreetingFriendFormService greetingFriendFormService;

    @GetMapping(path = "/auto-approved")
    public ResponseEntity<BaseResponse> autoApproval(@RequestParam(required = true) int uid) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity userEntity = userService.updateStatus(uid, UserStatus.APPROVED, null);
            if (userEntity.getStatus() == StatusUser.APPROVED) {
                List<GreetingFriendPromotionEntity> promotionEntities = greetingFriendPromotionService.findActive(userEntity);
                greetingFriendFormService.checkAndActiveGreetingNewFriendPromotion(userEntity, promotionEntities);
            }
            response.setError(0);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


}
