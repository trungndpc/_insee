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
import vn.insee.admin.retailer.controller.converter.MatchFootballConverter;
import vn.insee.admin.retailer.controller.converter.PredictMatchFootballFormConverter;
import vn.insee.admin.retailer.controller.dto.MatchFootballDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.PredictMatchFootballDTO;
import vn.insee.admin.retailer.service.FootballPromotionService;
import vn.insee.admin.retailer.service.MatchFootballService;
import vn.insee.admin.retailer.service.PredictFootballFormService;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;
import vn.insee.jpa.repository.PredictFootballMatchRepository;

@RestController
@RequestMapping("/api/match-football-form")
public class PredictMatchFootballFormController {
    private static final Logger LOGGER = LogManager.getLogger(PredictMatchFootballFormController.class);

    @Autowired
    private PredictFootballFormService matchFootballService;

    @Autowired
    private PredictMatchFootballFormConverter converter;


    @GetMapping(path = "/list")
    public ResponseEntity<BaseResponse> find(@RequestParam(required = true) int matchId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<PredictMatchFootballFormEntity> entities = matchFootballService.list(matchId, page, pageSize);
            PageDTO<PredictMatchFootballDTO> dto =
                    converter.convertToPageDTO(entities);
            response.setData(dto);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }






}
