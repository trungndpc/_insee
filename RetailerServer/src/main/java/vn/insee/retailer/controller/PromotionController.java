package vn.insee.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.common.ErrorCommon;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.controller.converter.PostConverter;
import vn.insee.retailer.controller.converter.PromotionConverter;
import vn.insee.retailer.service.GreetingFriendFormService;
import vn.insee.retailer.service.PostService;
import vn.insee.retailer.service.PromotionService;
import vn.insee.retailer.util.AuthenticationUtils;

import java.util.List;

import static vn.insee.common.ErrorCommon.INVALID_FOR_YOU_GREETING_FRIEND_PROMOTION;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {
    private static final Logger LOGGER = LogManager.getLogger(PromotionController.class);

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionConverter promotionConverter;

    @Autowired
    private GreetingFriendFormService friendFormService;

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> getById(@RequestParam(required = true) int id, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try {
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            PromotionEntity promotionEntity = promotionService.get(id);
            if (promotionEntity == null) {
                throw new Exception("promotion is not exits!");
            }
            if (!promotionService.isOpen(promotionEntity)) {
                response.setError(ErrorCommon.CLOSED_PROMOTION);
            } else if (!promotionService.isOpenFor(promotionEntity, user)) {
                response.setError(ErrorCommon.INVALID_LOCATION_PROMOTION);
            } else {
                if (promotionEntity.getType() == TypePromotion.GREETING_FRIEND) {
                    GreetingFriendFormEntity formEntity = friendFormService.getForm(user.getId(), promotionEntity.getId());
                    if (formEntity == null) {
                        response.setError(INVALID_FOR_YOU_GREETING_FRIEND_PROMOTION);
                    }else if (!friendFormService.isActive(formEntity)) {
                        response.setError(ErrorCommon.CLOSED_GREETING_FRIEND_PROMOTION);
                    }
                }

            }
            response.setData(promotionConverter.convert2DTO(promotionEntity));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
