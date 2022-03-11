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
import vn.insee.common.status.StatusGift;
import vn.insee.jpa.entity.GiftEntity;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.controller.converter.GiftConverter;
import vn.insee.retailer.controller.converter.PostConverter;
import vn.insee.retailer.controller.dto.GiftDTO;
import vn.insee.retailer.service.FormService;
import vn.insee.retailer.service.GiftService;
import vn.insee.retailer.service.PostService;
import vn.insee.retailer.util.AuthenticationUtils;

import java.util.List;

@RestController
@RequestMapping("/api/gift")
public class GiftController {
    private static final Logger LOGGER = LogManager.getLogger(GiftController.class);

    @Autowired
    private GiftService giftService;

    @Autowired
    private GiftConverter giftConverter;

    @Autowired
    private FormService formService;

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> getById(@RequestParam(required = true) int id, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            GiftEntity giftEntity = giftService.get(id);
            if (giftEntity == null) {
                throw new Exception("gift is not exits");
            }
            GiftDTO giftDTO = giftConverter.convert2DTO(giftEntity);
            response.setData(giftDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/update-status")
    public ResponseEntity<BaseResponse> updateStatus(@RequestParam(required = true) int id,
                                                     @RequestParam(required = true) int status, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            giftService.updateStatus(id, status);
            if (status == StatusGift.RECEIVED) {
                formService.updateReceivedStatusGift(id);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


}
