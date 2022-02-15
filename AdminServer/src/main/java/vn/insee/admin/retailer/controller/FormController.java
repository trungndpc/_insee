package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.FormConverter;
import vn.insee.admin.retailer.controller.dto.FormDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.StockFormDTO;
import vn.insee.admin.retailer.controller.dto.metric.FormCityMetricDTO;
import vn.insee.admin.retailer.controller.dto.metric.FormDateMetricDTO;
import vn.insee.admin.retailer.controller.dto.metric.UserDataMetricDTO;
import vn.insee.admin.retailer.service.FormService;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.jpa.metric.FormCityMetric;
import vn.insee.jpa.metric.FormDateMetric;
import vn.insee.jpa.metric.UserDataMetric;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/form")
public class FormController {
    private static final Logger LOGGER = LogManager.getLogger(FormController.class);

    @Autowired
    private FormService formService;

    @Autowired
    private FormConverter formConverter;

    @GetMapping(path = "/find-by-promotion")
    public ResponseEntity<BaseResponse> list( @RequestParam(required = true) int promotionId,
            @RequestParam(required = false) Integer city,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<FormEntity> formEntities = formService.findByPromotionId(promotionId, status, city, search, page, pageSize);
            PageDTO<FormDTO> promotionDTOPageDTO =
                    formConverter.convertToPageDTO(formEntities);
            response.setData(promotionDTOPageDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/count")
    public ResponseEntity<BaseResponse> stats(@RequestParam(required = false) List<Integer> promotionIds,
                                              @RequestParam(required = false) Integer status) {
        BaseResponse response = new BaseResponse();
        try{
            long count = formService.count(promotionIds, status);
            response.setData(count);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }



    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = true) int id) {
        BaseResponse response = new BaseResponse();
        try{
            FormEntity formEntity = formService.getById(id);
            if (formEntity.getType() == TypePromotion.STOCK_PROMOTION_TYPE) {
                StockFormDTO stockFormDTO = formConverter.convert2StockFormDTO((StockFormEntity) formEntity);
                response.setData(stockFormDTO);
            }else {
                FormDTO formDTO = formConverter.convert2FormDTO(formEntity);
                response.setData(formDTO);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/update-status")
    public ResponseEntity<BaseResponse> updateStatus(@RequestParam(required = true) int id,
                                                     @RequestParam(required = true) int status,
                                                     @RequestParam(required = false) String note) {
        BaseResponse response = new BaseResponse();
        try{
            formService.updateStatus(id, status, note);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/stats-form-date")
    public ResponseEntity<BaseResponse> statisticUserByDate(@RequestParam(required = false) List<Integer> promotionIds) {
        BaseResponse response = new BaseResponse();
        try{
            List<FormDateMetric> formDateMetrics = formService.statisticFormByDate(promotionIds);
            List<FormDateMetricDTO> dtos = formDateMetrics.stream().map(userDataMetric -> {
                return formConverter.map(userDataMetric);
            }).collect(Collectors.toList());
            response.setData(dtos);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/stats-form-city")
    public ResponseEntity<BaseResponse> statisticUserByCity(@RequestParam(required = false) List<Integer> promotionIds) {
        BaseResponse response = new BaseResponse();
        try{
            List<FormCityMetric> formCityMetrics = formService.statisticFormByCity(promotionIds);
            List<FormCityMetricDTO> dtos = formCityMetrics.stream().map(userDataMetric -> {
                return formConverter.map(userDataMetric);
            }).collect(Collectors.toList());
            response.setData(dtos);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }



}
