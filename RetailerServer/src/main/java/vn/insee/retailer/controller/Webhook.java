package vn.insee.retailer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.webhook.ZaloEventManager;


@RestController
@RequestMapping("/zalo")
public class Webhook {

    @Autowired
    private ZaloEventManager eventManager;

    private static final Logger LOGGER = LogManager.getLogger(Webhook.class);

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/webhook", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> zaloWebhook(@RequestBody String body)  {
        BaseResponse response = new BaseResponse(ErrorCode.SUCCESS);
        try{
            eventManager.doing(new JSONObject(body));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return ResponseEntity.ok(response);
    }



}
