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
import vn.insee.jpa.entity.CustomerEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.controller.converter.CustomerConverter;
import vn.insee.retailer.controller.form.RegisterForm;
import vn.insee.retailer.service.CustomerService;
import vn.insee.retailer.util.AuthenticationUtils;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);
    @Autowired
    private CustomerConverter customerConverter;

    @Autowired
    private CustomerService customerService;


    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> post(@RequestBody RegisterForm form, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            if (user.getCustomerId() != null || user.getCustomerId() != 0) {
                throw new Exception("user is registered");
            }
            CustomerEntity customerEntity = customerConverter.convert2Entity(form);
            customerEntity = customerService.register(user, customerEntity);
            response.setData(customerConverter.convert2DTO(customerEntity));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);

    }
}
