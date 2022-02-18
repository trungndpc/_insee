package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.PostConverter;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.PostDTO;
import vn.insee.admin.retailer.controller.dto.PromotionDTO;
import vn.insee.admin.retailer.controller.form.PostForm;
import vn.insee.admin.retailer.service.PostService;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.PromotionEntity;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private static final Logger LOGGER = LogManager.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private PostConverter postConverter;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> create(@RequestBody PostForm form) {
        BaseResponse response = new BaseResponse();
        try{
                PostEntity postEntity = postConverter.convert2Entity(form);
                postEntity = postService.create(postEntity);
                response.setData(postConverter.convert2DTO(postEntity));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<BaseResponse> find(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<PostEntity> postEntityPage = postService.find(page, pageSize);
            PageDTO<PostDTO> postDTOPageDTO =
                    postConverter.convertToPageDTO(postEntityPage);
            response.setData(postDTOPageDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get-list")
    public ResponseEntity<BaseResponse> getList() {
        BaseResponse response = new BaseResponse();
        try{
            List<PostEntity> postEntityList = postService.getAll();
            response.setData(postConverter.convert2ListDTO(postEntityList));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = true) int id) {
        BaseResponse response = new BaseResponse();
        try{
            PostEntity postEntity = postService.get(id);
            PostDTO postDTO = postConverter.convert2DTO(postEntity);
            response.setData(postDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/update-status")
    public ResponseEntity<BaseResponse> updateStatus(@RequestParam(required = true) int id,
                                                     @RequestParam(required = true) int status) {
        BaseResponse response = new BaseResponse();
        try{
            PostEntity entity = postService.updateStatus(id, status);
            PostDTO postDTO = postConverter.convert2DTO(entity);
            response.setData(postDTO);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }




}
