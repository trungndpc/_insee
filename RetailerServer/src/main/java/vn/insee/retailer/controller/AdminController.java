package vn.insee.retailer.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.service.MatchFootBallService;
import vn.insee.retailer.service.PredictFootballPromotionService;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.webhook.LightingEvent;
import vn.insee.retailer.webhook.PredictFootballEvent;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

    @Autowired
    private LightingEvent lightingEvent;

    @Autowired
    private PredictFootballEvent predictFootballEvent;

    @Autowired
    private PredictFootballPromotionService predictFootballPromotionService;

    @Autowired
    private MatchFootBallService matchFootBallService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/start-topic-lq-game")
    public ResponseEntity<BaseResponse> startTopicLQGame(@RequestParam(required = true) int promotionId,
                                                @RequestParam(required = true) String topicId) {
        BaseResponse response = new BaseResponse();
        try{
            lightingEvent.forceStart(promotionId, topicId);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/start-predict-football")
    public ResponseEntity<BaseResponse> startPredictFootball(@RequestParam(required = true) int promotionId,
                                                             @RequestParam(required = true) int matchId,
                                                             @RequestParam(required = true) int uid) {
        BaseResponse response = new BaseResponse();
        try{
            LOGGER.error("START>........");
            PredictFootballPromotionEntity promotionEntity = predictFootballPromotionService.findById(promotionId);
            MatchFootballEntity footballEntity = matchFootBallService.get(matchId);
            UserEntity userEntity = userService.findById(uid);
            predictFootballEvent.start(promotionEntity, userEntity, footballEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
