package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.AccumulationConverter;
import vn.insee.admin.retailer.controller.converter.MatchFootballConverter;
import vn.insee.admin.retailer.controller.dto.MatchFootballDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.service.AccumulationService;
import vn.insee.admin.retailer.service.FootballPromotionService;
import vn.insee.admin.retailer.service.MatchFootballService;
import vn.insee.admin.retailer.service.PredictFootballFormService;
import vn.insee.common.status.MatchFootballStatus;
import vn.insee.common.status.PredictMatchFootballStatus;
import vn.insee.common.type.TypeAccumulation;
import vn.insee.jpa.entity.AccumulationEntity;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/accumulation")
public class AccumulationController {
    private static final Logger LOGGER = LogManager.getLogger(AccumulationController.class);

    @Autowired
    private AccumulationConverter accumulationConverter;

    @Autowired
    private AccumulationService accumulationService;

    @Autowired
    private PredictFootballFormService predictFootballFormService;

    @GetMapping(path = "/list")
    public ResponseEntity<BaseResponse> find(@RequestParam(required = true) int promotionId,
            @RequestParam(required = true) int type,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<AccumulationEntity> entityPage = accumulationService.findByPromotionAndType(promotionId, type, page, pageSize);
            response.setData(accumulationConverter.convertToPageDTO(entityPage));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }



//    @EventListener
//    public void recheck(ContextRefreshedEvent event) {
//        System.out.println("DONE");
//        int wrong = 0;
//        List<AccumulationEntity> all = accumulationService.getAll();
//        for (AccumulationEntity entity: all) {
//            long count = predictFootballFormService.countUserByStatus(entity.getUid(), PredictMatchFootballStatus.CORRECT_TEAM);
//            Integer now = entity.getPoint();
//            if (count != now) {
//                entity.setPoint((int) count);
//                System.out.println("CURRENT: " + now + " | WRONG: " + count);
//                wrong++;
//            }
//        }
//        System.out.println("WRONG: " + wrong);
//    }


}
