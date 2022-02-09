package vn.insee.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.controller.converter.StockFormConverter;
import vn.insee.retailer.controller.form.StockForm;
import vn.insee.retailer.service.StockFormService;
import vn.insee.retailer.util.AuthenticationUtils;

@RestController
@RequestMapping("/api/stock-form")
public class StockFormController {
    private static final Logger LOGGER = LogManager.getLogger(StockFormController.class);

    @Autowired
    private StockFormConverter stockFormConverter;

    @Autowired
    private StockFormService stockFormService;


    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> post(@RequestBody StockForm form, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            StockFormEntity stockFormEntity = stockFormConverter.convert2Entity(form);
            stockFormEntity.setUserId(user.getId());
            stockFormEntity =  stockFormService.create(stockFormEntity);
            if (stockFormEntity == null) {
                response.setError(ErrorCode.FAILED);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
