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
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.controller.converter.FormConverter;
import vn.insee.retailer.controller.converter.PromotionConverter;
import vn.insee.retailer.controller.dto.FormDTO;
import vn.insee.retailer.controller.dto.PromotionDTO;
import vn.insee.retailer.service.FormService;
import vn.insee.retailer.service.PromotionService;
import vn.insee.retailer.util.AuthenticationUtils;

import java.util.List;


@RestController
@RequestMapping("/api/promotion")
public class PromotionController {
    private static final Logger LOGGER = LogManager.getLogger(PromotionController.class);

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionConverter promotionConverter;


    @Autowired
    private FormConverter formConverter;

    @Autowired
    private FormService formService;

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = true) int id, Authentication auth) {
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

            PromotionDTO promotionDTO = promotionConverter.convert2DTO(promotionEntity);
            List<FormEntity> formEntities = formService.findByUserIdAndPromotionId(user.getId(), promotionEntity.getId());
            if (formEntities != null) {
                List<FormDTO> formDTOS = formConverter.convert2ListDTO(formEntities);
                promotionDTO.setForms(formDTOS);
            }
            response.setData(promotionDTO);
            if (!promotionService.isOpen(promotionEntity)) {
                response.setError(ErrorCommon.CLOSED_PROMOTION);
            } else if (!promotionService.isOpenFor(promotionEntity, user)) {
                response.setError(ErrorCommon.INVALID_LOCATION_PROMOTION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
