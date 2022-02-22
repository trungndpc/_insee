package vn.insee.retailer.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.webhook.LightingEvent;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

    @Autowired
    private LightingEvent lightingEvent;

    @GetMapping(path = "/start-topic-lq-game")
    public ResponseEntity<BaseResponse> getById(@RequestParam(required = true) int promotionId,
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
}
