package vn.insee.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.controller.converter.FormConverter;
import vn.insee.retailer.controller.dto.FormHistoryDTO;
import vn.insee.retailer.service.FormService;
import vn.insee.retailer.util.AuthenticationUtils;

import java.util.List;


@RestController
@RequestMapping("/api/form")
public class FormController {
    private static final Logger LOGGER = LogManager.getLogger(FormController.class);

    @Autowired
    private FormService formService;

    @Autowired
    private FormConverter formConverter;

    @GetMapping(path = "/me")
    public ResponseEntity<BaseResponse> get(Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            List<FormEntity> formEntities = formService.findByUserId(user.getId());
            if (formEntities != null) {
                List<FormHistoryDTO> formHistoryDTOS = formConverter.convert2ListFormHistoryDTO(formEntities);
                response.setData(formHistoryDTOS);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
