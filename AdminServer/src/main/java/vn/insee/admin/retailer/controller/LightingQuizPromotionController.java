package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.LQConverter;
import vn.insee.admin.retailer.controller.dto.LQPromotionDTO;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.admin.retailer.controller.form.LightingQuizTopicForm;
import vn.insee.admin.retailer.controller.form.QuestionForm;
import vn.insee.admin.retailer.service.LightingQuizFormService;
import vn.insee.admin.retailer.service.LightingQuizPromotionService;
import vn.insee.common.status.StatusTopicLightingQuizPromotion;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;

@RestController
@RequestMapping("/api/promotion/lighting-quiz")
public class LightingQuizPromotionController {
    private static final Logger LOGGER = LogManager.getLogger(LightingQuizPromotionController.class);
    @Autowired
    private LightingQuizPromotionService lightingQuizPromotionService;

    @Autowired
    private LightingQuizFormService lightingQuizFormService;

    @Autowired
    private LQConverter lqConverter;

    @PostMapping(path = "/create-topic", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> create(@RequestBody LightingQuizTopicForm form,
                                               @RequestParam(required = true) int promotionId) {
        BaseResponse response = new BaseResponse();
        try{
            form.setStatus(StatusTopicLightingQuizPromotion.INIT);
            LightingQuizPromotionEntity promotionEntity = lightingQuizPromotionService.get(promotionId);
            promotionEntity = lqConverter.convert2Entity(promotionEntity, form);
            promotionEntity = lightingQuizPromotionService.update(promotionEntity);
            if (promotionEntity == null) {
                response.setError(ErrorCode.FAILED);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/create-or-update-question", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> createOrUpdate(@RequestBody QuestionForm form, @RequestParam(required = true) int promotionId,
    @RequestParam(required = true) String topicId) {
        BaseResponse response = new BaseResponse();
        try{
            LightingQuizPromotionEntity promotionEntity = lightingQuizPromotionService.get(promotionId);
            promotionEntity = lqConverter.convert2Entity(promotionEntity, topicId, form);
            promotionEntity = lightingQuizPromotionService.update(promotionEntity);
            if (promotionEntity == null) {
                response.setError(ErrorCode.FAILED);
            }
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
            LightingQuizPromotionEntity lightingQuizPromotionEntity = lightingQuizPromotionService.get(id);
            LQPromotionDTO lqPromotionDTO = lqConverter.convert2DTO(lightingQuizPromotionEntity);
            response.setData(lqPromotionDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get-topic")
    public ResponseEntity<BaseResponse> getTopic(@RequestParam(required = true) int id,
                                                 @RequestParam(required = true) String topicId) {
        BaseResponse response = new BaseResponse();
        try{
            LightingQuizPromotionEntity lightingQuizPromotionEntity = lightingQuizPromotionService.get(id);
            TopicDTO topicDTO = lqConverter.getTopicDTO(lightingQuizPromotionEntity, topicId);
            if (topicDTO != null) {
                response.setData(topicDTO);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/update-status-topic")
    public ResponseEntity<BaseResponse> endTopic(@RequestParam(required = true) int id,
                                                 @RequestParam(required = true) String topicId,
                                                 @RequestParam(required = true) int status) {
        BaseResponse response = new BaseResponse();
        try{
            LightingQuizPromotionEntity lightingQuizPromotionEntity = lightingQuizPromotionService.get(id);
            TopicDTO topicDTO = lqConverter.getTopicDTO(lightingQuizPromotionEntity, topicId);
            lightingQuizPromotionService.updateStatusTopic(lightingQuizPromotionEntity.getId(), topicId,
                    status);
            if (status == StatusTopicLightingQuizPromotion.DONE) {
                lightingQuizFormService.summary2Ranking(lightingQuizPromotionEntity.getId(), topicDTO);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


}
