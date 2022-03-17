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

    @GetMapping(value = "/test", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response) throws
            IOException {

        UserEntity userEntity = userService.findByPhone("0972797184");
        JSONObject data = new JSONObject();
        data.put("customer_name", userEntity.getName());
        data.put("product_name", "Xi măng");
        data.put("customer_code", userEntity.getInseeId());
        data.put("order_date", "01/01/2022");
        String phone = userEntity.getPhone();
        if (phone.startsWith("0")) {
            phone = phone.replace("0", "84");
        }
        ZaloService.INSTANCE.sendZNS(phone, "221294", userEntity.getPhone(), data);
        return "OK";
    }

    @GetMapping(value = "/**", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        return RenderUtils.render("index.html");
    }

}
