package vn.insee.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.controller.converter.UserConverter;
import vn.insee.retailer.controller.dto.UserDTO;
import vn.insee.retailer.controller.form.RegisterForm;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.util.AuthenticationUtils;
import vn.insee.retailer.util.StringUtils;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/me")
    public ResponseEntity<BaseResponse> get(Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            user = userService.findById(user.getId());
            UserDTO userDTO = userConverter.convert2DTO(user);
            response.setData(userDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> post(@RequestBody RegisterForm form, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            user.setPhone(form.getPhone());
            if (!StringUtils.isEmpty(form.getName())) {
                user.setName(form.getName());
            }

            if (form.getCityId() != null && form.getCityId() != 0) {
                user.setCityId(form.getCityId());
            }

            if (form.getDistrictId() != null && form.getDistrictId() != 0) {
                user.setDistrictId(form.getDistrictId());
            }

            if (!StringUtils.isEmpty(form.getAddress())) {
                user.setAddress(form.getAddress());
            }

            if (form.getCements() != null && !form.getCements().isEmpty()) {
                user.setProducts(form.getCements());
            }
            user = userService.register(user);
            response.setData(userConverter.convert2DTO(user));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/check-phone")
    public ResponseEntity<BaseResponse> checkPhone(@RequestParam(required = true) String phone, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            UserEntity byPhone = userService.findByPhone(phone);
            if (byPhone == null) {
                response.setError(-9);
            }else {
                response.setError(byPhone.getStatus());
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
