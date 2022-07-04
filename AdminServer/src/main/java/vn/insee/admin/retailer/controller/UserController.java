package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.common.UserStatus;
import vn.insee.admin.retailer.controller.converter.UserConverter;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.admin.retailer.controller.dto.dashboard.CountUserDTO;
import vn.insee.admin.retailer.controller.dto.metric.UserDataMetricDTO;
import vn.insee.admin.retailer.controller.exporter.UserExcelExporter;
import vn.insee.admin.retailer.controller.form.CustomerForm;
import vn.insee.admin.retailer.controller.importer.CustomerExcelImporter;
import vn.insee.admin.retailer.service.GreetingFriendFormService;
import vn.insee.admin.retailer.service.GreetingFriendPromotionService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.common.Permission;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.GreetingFriendPromotionEntity;
import vn.insee.jpa.metric.UserCityMetric;
import vn.insee.jpa.metric.UserDataMetric;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private GreetingFriendPromotionService greetingFriendPromotionService;

    @Autowired
    private GreetingFriendFormService greetingFriendFormService;


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


    @GetMapping(path = "/export-excel")
    public void exportExcel(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer city,
            HttpServletResponse response) {
        try{
            List<UserEntity> list = userService.list(search, status, city);
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            list = list.stream().sorted((a, b) -> a.getCreatedTime().compareTo(b.getCreatedTime())).collect(Collectors.toList());
            UserExcelExporter excelExporter = new UserExcelExporter(list);
            excelExporter.export(response);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
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
                                                     @RequestParam(required = true) int status,
                                                     @RequestParam(required = false) String note) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity userEntity = userService.updateStatus(uid, status, note);
            //approved chao ban moi
            if (userEntity.getStatus() == StatusUser.APPROVED && status != StatusUser.APPROVED) {
                checkAndActiveGreetingNewFriendPromotion(userEntity);
            }
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
            countUserDTO.setNumUser(userService.count(location, Arrays.asList(UserStatus.APPROVED,
                    UserStatus.WAITING_ACTIVE, UserStatus.WAIT_APPROVAL)));
            countUserDTO.setNumApprovedUser(userService.count(location, Arrays.asList(UserStatus.APPROVED)));
            countUserDTO.setNumWaitingActiveUser(userService.count(location, Arrays.asList(UserStatus.WAITING_ACTIVE)));
            countUserDTO.setNumWaitingReviewUser(userService.count(location, Arrays.asList(UserStatus.WAIT_APPROVAL)));
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
            List<UserDataMetric> userDataMetrics = userService.statisticUserByDate(Arrays.asList(StatusUser.APPROVED));
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


    @PostMapping("/upload-customer-excel")
    public ResponseEntity<BaseResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        BaseResponse response = new BaseResponse();
        try{
            List<UserEntity> entityList = CustomerExcelImporter.INSTANCE.read(file.getInputStream());
            if (entityList == null) {
                throw new Exception("can not parse file");
            }
            if (entityList.size() <= 0) {
                throw new Exception("file is empty");
            }
            entityList = entityList.stream().filter(user -> userService.findByPhone(user.getPhone()) == null)
                    .collect(Collectors.toList());
//            for (UserEntity user : entityList) {
//                if (userService.findByPhone(user.getPhone()) != null) {
//                    throw new Exception("phone is exits : "  + user.getPhone());
//                }
//            }
            for (UserEntity user: entityList) {
                user.setRoleId(Permission.RETAILER.getId());
                user.setStatus(UserStatus.WAITING_ACTIVE);
                userService.saveOrUpdate(user);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    private void checkAndActiveGreetingNewFriendPromotion(UserEntity userEntity) {
        List<GreetingFriendPromotionEntity> list = greetingFriendPromotionService.findActive(userEntity);
        if (list != null) {
            list.forEach(entity -> {
                greetingFriendFormService.activeGreetingNewFriendPromotion(entity, userEntity);
            });
        }
    }
}
