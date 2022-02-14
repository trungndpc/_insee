package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.LQConverter;
import vn.insee.admin.retailer.controller.converter.PromotionConverter;
import vn.insee.admin.retailer.controller.converter.StockConverter;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.PromotionDTO;
import vn.insee.admin.retailer.controller.form.PromotionForm;
import vn.insee.admin.retailer.service.LightingQuizPromotionService;
import vn.insee.admin.retailer.service.PromotionService;
import vn.insee.admin.retailer.service.StockPromotionService;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.entity.promotion.StockPromotionEntity;

import java.util.List;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {
    private static final Logger LOGGER = LogManager.getLogger(PromotionConverter.class);
    @Autowired
    private PromotionService promotionService;

    @Autowired
    private StockPromotionService stockPromotionService;

    @Autowired
    private LightingQuizPromotionService lightingQuizPromotionService;

    @Autowired
    private PromotionConverter promotionConverter;

    @Autowired
    private StockConverter stockConverter;

    @Autowired
    private LQConverter lqConverter;


    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> create(@RequestBody PromotionForm form) {
        BaseResponse response = new BaseResponse();
        try{
            if (form.getType() == TypePromotion.STOCK_PROMOTION_TYPE) {
                StockPromotionEntity stockPromotionEntity = stockConverter.convert2Entity(form);
                stockPromotionEntity = stockPromotionService.create(stockPromotionEntity);
                response.setData(promotionConverter.convert2DTO(stockPromotionEntity));
            }else if (form.getType() == TypePromotion.LIGHTING_QUIZ_GAME_PROMOTION_TYPE) {
                LightingQuizPromotionEntity lightingQuizPromotionEntity = lqConverter.convert2Entity(form);
                lightingQuizPromotionEntity = lightingQuizPromotionService.create(lightingQuizPromotionEntity);
                response.setData(promotionConverter.convert2DTO(lightingQuizPromotionEntity));
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<BaseResponse> list(
            @RequestParam(required = false) List<Integer> types,
            @RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<PromotionEntity> promotionEntityPage = promotionService.find(types, page, pageSize);
            PageDTO<PromotionDTO> promotionDTOPageDTO =
                    promotionConverter.convertToPageDTO(promotionEntityPage);
            response.setData(promotionDTOPageDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/list-promotion-for-map-post")
    public ResponseEntity<BaseResponse> listPromotionForMapPost() {
        BaseResponse response = new BaseResponse();
        try{
            List<PromotionEntity> promotionForMapPost = promotionService.findPromotionForMapPost();
            response.setData(promotionConverter.covert2DTOOnlyIdAndName(promotionForMapPost));
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = true) int id) {
        BaseResponse response = new BaseResponse();
        try{
            PromotionEntity promotionEntity = promotionService.get(id);
            PromotionDTO promotionDTO = promotionConverter.convert2DTO(promotionEntity);
            response.setData(promotionDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


}
