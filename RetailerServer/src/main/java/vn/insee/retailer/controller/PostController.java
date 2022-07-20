package vn.insee.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.retailer.common.BaseResponse;
import vn.insee.retailer.common.ErrorCode;
import vn.insee.retailer.controller.converter.PostConverter;
import vn.insee.retailer.service.GreetingFriendFormService;
import vn.insee.retailer.service.PostService;
import vn.insee.retailer.service.PromotionService;
import vn.insee.retailer.util.AuthenticationUtils;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private static final Logger LOGGER = LogManager.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private PostConverter postConverter;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private GreetingFriendFormService friendFormService;


    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = true) int id, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            PostEntity postEntity = postService.get(id);
            response.setData(postConverter.convert2DTO(postEntity));
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<BaseResponse> list(Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("not permission");
            }
            List<PostEntity> posts = postService.findPost(user);
            if (posts != null && !posts.isEmpty()) {
                posts = posts.stream().filter(postEntity -> this.isShowPost(postEntity, user))
                        .collect(Collectors.toList());
            }
            response.setData(postConverter.covert2DTOs(posts));
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public boolean isShowPost(PostEntity postEntity, UserEntity userEntity) {
        Integer promotionId = postEntity.getPromotionId();
        if (promotionId == null) {
            return true;
        }

        PromotionEntity promotionEntity = promotionService.get(promotionId);
        if (!promotionService.isOpenFor(promotionEntity, userEntity)) {
            return false;
        }

        if (promotionEntity.getType() == TypePromotion.GREETING_FRIEND) {
            GreetingFriendFormEntity serviceForm = friendFormService.getForm(userEntity.getId(), promotionEntity.getId());
            if (serviceForm == null) {
                return false;
            }
            return friendFormService.isActive(serviceForm);
        }

        return true;
    }

}
