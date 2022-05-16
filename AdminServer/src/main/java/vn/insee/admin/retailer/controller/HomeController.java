package vn.insee.admin.retailer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.admin.retailer.controller.converter.UserConverter;
import vn.insee.admin.retailer.service.AccumulationService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.admin.retailer.util.ReadFileExcelUtil;
import vn.insee.admin.retailer.util.RenderUtils;
import vn.insee.admin.retailer.woker.Scheduler;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.common.Permission;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.repository.LightingQuizPromotionRepository;
import vn.insee.jpa.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


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
    private Scheduler scheduler;

    @Autowired
    private LightingQuizPromotionRepository lightingQuizPromotionRepository;

    @Autowired
    private UserRepository userRepository;

    private AccumulationService accumulationService;

    @GetMapping(value = "/**", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        return RenderUtils.render("index.html");
    }

}
