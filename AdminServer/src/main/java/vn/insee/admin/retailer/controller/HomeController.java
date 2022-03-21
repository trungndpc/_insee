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

    @GetMapping(value = "/test", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response) throws
            IOException {

        LightingQuizPromotionEntity one = lightingQuizPromotionRepository.getOne(124);
        one.setTopics("[{\"id\":\"topic_id_f9d08248-9c1e-49ab-98bc-2b5468de24b8\",\"title\":\"Minigame Nhanh Như Chớp: Vui cùng INSEE\",\"questions\":[{\"id\":\"question_id_cd7b3580-9dfe-4637-b975-e3694a897092\",\"content\":\"Điểm cực Tây của nước ta nằm ở tỉnh thành nào?\",\"options\":[{\"id\":\"answer_id_a9f57637-f0dc-439c-a088-b8a1be16ffec\",\"content\":\"Điện Biên\",\"right\":true},{\"id\":\"answer_id_7d29557e-a7e6-4388-97e6-a4f26b304c87\",\"content\":\"Hà Giang\",\"right\":false},{\"id\":\"answer_id_cfa10033-08cc-4fb4-8073-9e84217a91de\",\"content\":\"Cà Mau\",\"right\":false}]},{\"id\":\"question_id_9a147fc9-b957-41df-96c6-39e4aeb22c14\",\"content\":\"Theo cách tính giờ của người xưa, một canh trước đây dài bằng mấy giờ?\",\"options\":[{\"id\":\"answer_id_f4509d9d-b6bf-410e-ba31-f60e6a7acb8c\",\"content\":\"1\",\"right\":false},{\"id\":\"answer_id_7d55deab-04eb-4624-a0d3-f2ec9545c93e\",\"content\":\"2\",\"right\":true},{\"id\":\"answer_id_1682701c-ca2d-420b-b057-dff34883751d\",\"content\":\"3\",\"right\":false}]},{\"id\":\"question_id_90e7870a-9b6d-40a4-aeb2-2df3d6b9b0bb\",\"content\":\"Gấu bắc cực có da màu gì?\",\"options\":[{\"id\":\"answer_id_c14bfa43-c84a-49b1-8239-3fbfb77ed60e\",\"content\":\"Trắng\",\"right\":false},{\"id\":\"answer_id_20b79327-797c-430b-a3fe-d280e0c3b95e\",\"content\":\"Đen\",\"right\":true},{\"id\":\"answer_id_c32a21d2-95fb-4cf0-86f2-86a4d5e1ce47\",\"content\":\"Hồng\",\"right\":false}]},{\"id\":\"question_id_d8a2c2b0-1275-440e-bd49-1d08f6d3bbcb\",\"content\":\" Dùng chất nào sau đây có thể tẩy vết rượu vang đỏ trên vải?\",\"options\":[{\"id\":\"answer_id_54cd70d0-f6fe-4f34-b9ff-df16fe62b774\",\"content\":\"Bột mì\",\"right\":false},{\"id\":\"answer_id_de99bcaa-eae5-459d-b40e-e4188253dea1\",\"content\":\"Đường\",\"right\":false},{\"id\":\"answer_id_92df387a-4c89-4a90-a77a-b17a1f9a5450\",\"content\":\"Muối\",\"right\":true}]},{\"id\":\"question_id_b1963856-18ce-4f50-ad7e-0d0eb9da38e4\",\"content\":\"Đàn tranh còn có tên gọi khác là gì?\",\"options\":[{\"id\":\"answer_id_8ed44bd9-dbfa-4a21-82ee-297c24716f0f\",\"content\":\" Đàn tì bà\",\"right\":false},{\"id\":\"answer_id_dd401402-1fe4-47ec-b1ae-fa7817904d61\",\"content\":\"Đàn nguyệt cầm\",\"right\":false},{\"id\":\"answer_id_2e0da067-35e4-4c3b-b0dc-872593cfdf12\",\"content\":\"Đàn thập lục\",\"right\":true}]}],\"timeStart\":1647518400000,\"timeEnd\":1647691200000,\"status\":1}]");
        lightingQuizPromotionRepository.saveAndFlush(one);
        return "OK";
    }

    @GetMapping(value = "/**", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        return RenderUtils.render("index.html");
    }

}
