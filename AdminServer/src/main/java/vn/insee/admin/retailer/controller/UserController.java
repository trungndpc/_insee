package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.common.UserStatus;
import vn.insee.admin.retailer.controller.converter.UserConverter;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.admin.retailer.controller.dto.dashboard.CountUserDTO;
import vn.insee.admin.retailer.controller.dto.metric.UserDataMetricDTO;
import vn.insee.admin.retailer.controller.form.CustomerForm;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.metric.UserCityMetric;
import vn.insee.jpa.metric.UserDataMetric;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = true) int id) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity userEntity = userService.findById(id);
            UserDTO userDTO = userConverter.convert2DTO(userEntity);
            response.setData(userDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<BaseResponse> find(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer city,
            @RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<UserEntity> userEntityPage = userService.find(search, status, city, page, pageSize);
            PageDTO<UserDTO> userDTOPageDTO = userConverter.convertToPageDTO(userEntityPage);
            response.setData(userDTOPageDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/upload-customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> post(@RequestBody CustomerForm form) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity customer = userConverter.convert2Entity(form);
            customer = userService.createUserFromInseeCustomer(customer);
            UserDTO customerDTO = userConverter.convert2DTO(customer);
            response.setData(customerDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> post(@RequestBody CustomerForm form, @RequestParam(required = true) Integer id) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity userEntity = userService.findById(id);
            userEntity = userConverter.map(userEntity, form);
            userEntity = userService.update(userEntity);
            UserDTO customerDTO = userConverter.convert2DTO(userEntity);
            response.setData(customerDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/update-status")
    public ResponseEntity<BaseResponse> updateStatus(@RequestParam(required = true) int uid,
                                                     @RequestParam(required = true) int status) {
        BaseResponse response = new BaseResponse();
        try{
            userService.updateStatus(uid, status);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/count")
    public ResponseEntity<BaseResponse> count(@RequestParam(required = false) Integer location) {
        BaseResponse response = new BaseResponse();
        try{
            CountUserDTO countUserDTO = new CountUserDTO();
            countUserDTO.setNumUser(userService.count(location, null));
            countUserDTO.setNumApprovedUser(userService.count(location, UserStatus.APPROVED));
            countUserDTO.setNumWaitingActiveUser(userService.count(location, UserStatus.WAITING_ACTIVE));
            countUserDTO.setNumWaitingReviewUser(userService.count(location, UserStatus.WAIT_APPROVAL));
            response.setData(countUserDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/stats-user-date")
    public ResponseEntity<BaseResponse> statisticUserByDate() {
        BaseResponse response = new BaseResponse();
        try{
            List<UserDataMetric> userDataMetrics = userService.statisticUserByDate();
            List<UserDataMetricDTO> dtos = userDataMetrics.stream().map(userDataMetric -> {
                return userConverter.convert2DTO(userDataMetric);
            }).collect(Collectors.toList());
            response.setData(dtos);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/stats-user-city")
    public ResponseEntity<BaseResponse> statisticUserByCity() {
        BaseResponse response = new BaseResponse();
        try{
            List<UserCityMetric> userCityMetrics = userService.statisticUserByCity();
            List<UserCityMetric> dtos = userCityMetrics.stream().map(userCityMetric -> {
                return userConverter.convert2DTO(userCityMetric);
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
