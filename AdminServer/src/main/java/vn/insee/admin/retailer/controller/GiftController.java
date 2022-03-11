package vn.insee.admin.retailer.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.GiftConverter;
import vn.insee.admin.retailer.controller.dto.GiftDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.admin.retailer.controller.dto.dashboard.CountGiftDTO;
import vn.insee.admin.retailer.controller.dto.dashboard.CountPromotionDTO;
import vn.insee.admin.retailer.controller.form.GiftForm;
import vn.insee.admin.retailer.service.FormService;
import vn.insee.admin.retailer.service.GiftService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.common.status.StatusGift;
import vn.insee.common.status.StatusPromotion;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.GiftEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gift")
public class GiftController {
    private static final Logger LOGGER = LogManager.getLogger(GiftController.class);

    @Autowired
    private GiftConverter giftConverter;

    @Autowired
    private FormService formService;

    @Autowired
    private GiftService giftService;

    @Autowired
    private UserService userService;


    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> create(@RequestBody GiftForm form,
                                               @RequestParam(required = true) int formId) {
        BaseResponse response = new BaseResponse();
        try{
            GiftEntity giftEntity = giftConverter.convertFromForm(form);
            giftEntity.setStatus(StatusGift.SEND);
            giftEntity = giftService.create(giftEntity);
            UserEntity userEntity = userService.findById(giftEntity.getUserId());
            giftService.sendGift(giftEntity, userEntity);
            formService.addGift(formId, Arrays.asList(giftEntity.getId()));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/count")
    public ResponseEntity<BaseResponse> count() {
        BaseResponse response = new BaseResponse();
        try{
            long received = giftService.count(StatusGift.RECEIVED);
            long send = giftService.count(StatusGift.SEND);
            long total = giftService.count(null);
            CountGiftDTO countGiftDTO = new CountGiftDTO();
            countGiftDTO.setReceived((int) received);
            countGiftDTO.setSend((int) send);
            countGiftDTO.setTotal((int) total);
            response.setData(countGiftDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<BaseResponse> find(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<GiftEntity> giftEntities = giftService.find(status, page, pageSize);
            PageDTO<GiftDTO> giftDTOPageDTO = giftConverter.convertToPageDTO(giftEntities);
            response.setData(giftDTOPageDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
