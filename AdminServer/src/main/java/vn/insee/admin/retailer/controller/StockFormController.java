package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.LQFormConverter;
import vn.insee.admin.retailer.controller.form.PromotionForm;
import vn.insee.admin.retailer.controller.form.UpdateStockForm;
import vn.insee.admin.retailer.service.FormService;
import vn.insee.admin.retailer.service.LightingQuizFormService;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.entity.promotion.StockPromotionEntity;

@RestController
@RequestMapping("/api/stock-form")
public class StockFormController {
    private static final Logger LOGGER = LogManager.getLogger(StockFormController.class);

    @Autowired
    private FormService formService;

    @PostMapping(path = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> update(@RequestParam(required = true) int id, @RequestBody UpdateStockForm form) {
        BaseResponse response = new BaseResponse();
        try{
            StockFormEntity formEntity = (StockFormEntity) formService.getById(id);
            formEntity.setNote(form.getNote());
            formService.update(formEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


}
