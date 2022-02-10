package vn.insee.retailer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.common.Permission;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.repository.LightingQuizPromotionRepository;
import vn.insee.retailer.controller.converter.UserConverter;
import vn.insee.retailer.controller.dto.UserDTO;
import vn.insee.retailer.service.LightingQuizPromotionService;
import vn.insee.retailer.service.UserService;
import vn.insee.retailer.util.AuthenticationUtils;
import vn.insee.retailer.util.RenderUtils;
import vn.insee.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


@RestController
public class HomeController {
    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LightingQuizPromotionService lightingQuizPromotionService;

    @Autowired
    private LightingQuizPromotionRepository lightingQuizPromotionRepository;

    @GetMapping(value = "/test", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String test(Authentication auth) throws Exception {
        LightingQuizPromotionEntity one = lightingQuizPromotionRepository.getOne(14);
        JSONArray sJsonObject = new JSONArray("[{\"id\":\"topic_id_e3dde64c-3246-4f5c-8563-8f7efe5cb962\",\"title\":\"Chủ đề 1\",\"questions\":[{\"id\":\"question_id_2c17300e-91e2-4c02-91b0-6a7ddd79f444\",\"content\":\"Câu 1\",\"options\":[{\"id\":\"answer_id_3a44546d-26e9-44fa-8cab-ff53fa8e39b5\",\"content\":\"Đáp án A\",\"right\":true},{\"id\":\"answer_id_76c1597f-c45a-4a83-80b9-1921e24e5aaf\",\"content\":\"Đáp án B\",\"right\":false}]},{\"id\":\"question_id_a7dc8a35-484e-49f6-8044-82faf4b0ee24\",\"content\":\"Câu 2\",\"options\":[{\"id\":\"answer_id_d75b5916-ec27-4dd8-9e17-5726efd0c6b1\",\"content\":\"Câu A\",\"right\":false},{\"id\":\"answer_id_0a304fd1-6bfd-406a-b42e-a168cf423044\",\"content\":\"Câu B\",\"right\":true}]}],\"timeStart\":1644486000000,\"timeEnd\":1644500400000}]");
        one.setTopics(sJsonObject.toString());
        lightingQuizPromotionRepository.saveAndFlush(one);
        return "<<TEST>>";
    }

    @GetMapping(value = "/**", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index(Authentication auth, HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        UserEntity authUser = AuthenticationUtils.getAuthUser(auth);
        if (authUser == null) {
            String continueUrl = HttpUtil.getFullURL(request);
            response.sendRedirect("/dang-ky?continueUrl=" + HttpUtil.encodeUrl(continueUrl));
            return "OK";
        }
        if (!isRetailer(authUser)) {
            response.sendRedirect("/oops");
            return "OK";
        }
        UserEntity userEntity = userService.findById(authUser.getId());
        UserDTO userDTO = userConverter.convert2DTO(userEntity);
        String jsonCustomer = objectMapper.writeValueAsString(userDTO);
        return RenderUtils.render("index.html", "user", jsonCustomer);
    }

    private boolean isRetailer(UserEntity userEntity) {
        return userEntity.getRoleId() == Permission.RETAILER.getId();
    }

}
