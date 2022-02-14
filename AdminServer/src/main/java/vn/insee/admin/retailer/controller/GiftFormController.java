package vn.insee.admin.retailer.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.GiftConverter;
import vn.insee.admin.retailer.controller.form.GiftForm;
import vn.insee.admin.retailer.service.FormService;
import vn.insee.admin.retailer.service.GiftService;
import vn.insee.jpa.entity.GiftEntity;

import java.util.Arrays;

@RestController
@RequestMapping("/api/gift")
public class GiftFormController {
    private static final Logger LOGGER = LogManager.getLogger(GiftFormController.class);

    @Autowired
    private GiftConverter giftConverter;

    @Autowired
    private FormService formService;

    @Autowired
    private GiftService giftService;


    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> create(@RequestBody GiftForm form, @RequestParam(required = true) int formId) {
        BaseResponse response = new BaseResponse();
        try{
            GiftEntity giftEntity = giftConverter.convertFromForm(form);
            giftEntity = giftService.create(giftEntity);
            formService.addGift(formId, Arrays.asList(giftEntity.getId()));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
