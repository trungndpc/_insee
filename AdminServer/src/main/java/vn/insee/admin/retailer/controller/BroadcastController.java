package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.BroadcastConverter;
import vn.insee.admin.retailer.controller.converter.PostConverter;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.PostDTO;
import vn.insee.admin.retailer.controller.form.BroadcastForm;
import vn.insee.admin.retailer.controller.form.PostForm;
import vn.insee.admin.retailer.service.BroadcastService;
import vn.insee.admin.retailer.service.PostService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.common.status.StatusUser;
import vn.insee.common.type.TypeBroadcast;
import vn.insee.jpa.entity.BroadcastEntity;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/broadcast")
public class BroadcastController {
    private static final Logger LOGGER = LogManager.getLogger(BroadcastController.class);

    @Autowired
    private BroadcastConverter converter;

    @Autowired
    private BroadcastService service;

    @Autowired
    private UserService userService;


    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> create(@RequestBody BroadcastForm form) {
        BaseResponse response = new BaseResponse();
        try{
            BroadcastEntity broadcastEntity = converter.convert2Entity(form);
            List<Integer> uids = new ArrayList<>();
            if (broadcastEntity.getType() == TypeBroadcast.REQUEST_REGISTER_ZNS) {
                List<UserEntity> userEntities = userService.findBy(broadcastEntity.getCityIds(), broadcastEntity.getDistrictIds(),
                        StatusUser.WAITING_ACTIVE);
                if (userEntities != null) {
                    uids = userEntities.stream().map(u -> u.getId()).collect(Collectors.toList());
                }
            }
            if (broadcastEntity.getType() == TypeBroadcast.BROADCAST_NORMAL_POST) {
                List<UserEntity> userEntities = userService.findBy(broadcastEntity.getCityIds(), broadcastEntity.getDistrictIds(), StatusUser.APPROVED);
                if (userEntities != null) {
                    uids = userEntities.stream().map(u -> u.getId()).collect(Collectors.toList());
                }
            }
            broadcastEntity.setUserIds(uids);
            broadcastEntity = service.create(broadcastEntity);
            response.setData(converter.convert2DTO(broadcastEntity));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> update(@RequestParam(required = true) int id, @RequestBody BroadcastForm form) {
        BaseResponse response = new BaseResponse();
        try{
            BroadcastEntity broadcastEntity = service.get(id);
            broadcastEntity.setCityIds(form.getCityIds());
            broadcastEntity.setDistrictIds(form.getDistrictIds());
            broadcastEntity.setPostId(form.getPostId());
            broadcastEntity.setName(form.getName());
            broadcastEntity.setTimeStart(form.getTimeStart());
            service.update(broadcastEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/estimate")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = false) List<Integer> cityIds,
                                            @RequestParam(required = false) List<Integer> districtIds,
                                            @RequestParam(required = true) Integer type) {
        BaseResponse response = new BaseResponse();
        try{
            int status = type == TypeBroadcast.REQUEST_REGISTER_ZNS ? StatusUser.WAITING_ACTIVE : StatusUser.APPROVED;
            long count = userService.countBy(cityIds, districtIds, status);
            response.setData(count);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<BaseResponse> find(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<BroadcastEntity> broadcastEntities = service.find(page, pageSize);
            response.setData(converter.convertToPageDTO(broadcastEntities));
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/update-status")
    public ResponseEntity<BaseResponse> updateStatus(@RequestParam(required = false) int status, @RequestParam(required = true) int id) {
        BaseResponse response = new BaseResponse();
        try{
            service.updateStatus(id, status);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = true) int id) {
        BaseResponse response = new BaseResponse();
        try{
            BroadcastEntity broadcastEntity = service.get(id);
            response.setData(converter.convert2DTO(broadcastEntity));
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }






}
