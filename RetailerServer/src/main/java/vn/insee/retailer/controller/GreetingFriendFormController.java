package vn.insee.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.controller.converter.GreetingFriendFormConverter;
import vn.insee.retailer.controller.form.GreetingFriendForm;
import vn.insee.retailer.service.GreetingFriendFormService;
import vn.insee.retailer.util.AuthenticationUtils;

@RestController
@RequestMapping("/api/greeting-friend-form")
public class GreetingFriendFormController {
    private static final Logger LOGGER = LogManager.getLogger(GreetingFriendFormController.class);

    @Autowired
    private GreetingFriendFormConverter converter;

    @Autowired
    private GreetingFriendFormService service;


    @PostMapping(path = "/submit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> post(@RequestBody GreetingFriendForm form, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            GreetingFriendFormEntity entity = converter.convert2Entity(form);
            entity.setUserId(user.getId());
            entity =  service.submit(entity);
            if (entity == null) {
                response.setError(ErrorCode.FAILED);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
