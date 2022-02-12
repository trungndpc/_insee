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
        LightingQuizPromotionEntity one = lightingQuizPromotionRepository.getOne(39);
        JSONArray sJsonObject = new JSONArray("[{\"id\":\"topic_id_d8b02dac-a0d4-4142-bdd0-e07c0a8e87d1\",\"title\":\"Vì sao nhiều người thường mua muối, xin chữ đầu năm?\",\"questions\":[{\"id\":\"question_id_7da40a23-95c0-4c0d-bfd4-4125a8d281e9\",\"content\":\"Tục lệ “đầu năm mua muối” có ý nghĩa gì?\",\"options\":[{\"id\":\"answer_id_d0fdb712-375a-4fb2-8612-0bd4cc2c655b\",\"content\":\"Chào đón năm mới tài lộc\",\"right\":false},{\"id\":\"answer_id_546b0398-3a8c-40ec-8d9c-d8201bebdad4\",\"content\":\"Nhắc nhở tiết kiệm\",\"right\":false},{\"id\":\"answer_id_c2107f6d-781b-43f2-8a16-6e2e574dc68f\",\"content\":\"Cả hai đáp án trên\",\"right\":true}]},{\"id\":\"question_id_6320d261-aba0-4fbc-b819-cbcf4d88953a\",\"content\":\"Vì sao cuối năm người Việt thường mua vôi?\",\"options\":[{\"id\":\"answer_id_061820f9-3a19-40d4-b239-41429535a0ab\",\"content\":\"Xua đuổi tà ma, xui xẻo\",\"right\":true},{\"id\":\"answer_id_f81db124-dfea-4d9f-a913-4743404f881e\",\"content\":\"Trang hoàng nhà cửa\",\"right\":false},{\"id\":\"answer_id_1030fbe2-4bca-4381-9fe1-0b83bf9b7f46\",\"content\":\"Đánh dấu vị trí cho thần linh, gia tiên tìm về gia chủ\",\"right\":false}]},{\"id\":\"question_id_f1fd11be-924c-408e-aea8-60e786ae2a1f\",\"content\":\"Vôi trắng thường được rải ở đâu?\",\"options\":[{\"id\":\"answer_id_c74d6972-1e96-440a-a580-ac696d1e8335\",\"content\":\"Trước cửa nhà\",\"right\":false},{\"id\":\"answer_id_fdd88a8c-985c-454e-81ca-1830fd02b0d4\",\"content\":\"Trong bếp\",\"right\":false},{\"id\":\"answer_id_bfa0e072-40bc-4aa3-a2c7-078a6b3d642c\",\"content\":\"Bốn góc nhà\",\"right\":true}]},{\"id\":\"question_id_f63b1190-9256-4c3b-8b69-88d131fe99a6\",\"content\":\"Tục xin chữ đầu năm mang ý nghĩa?\",\"options\":[{\"id\":\"answer_id_61700fd5-ffdc-4549-ae9f-9f8910bad5a9\",\"content\":\"Lấy may\",\"right\":false},{\"id\":\"answer_id_05505592-fc6e-446d-b4b3-6ba913966af0\",\"content\":\"Thể hiện truyền thống trọng tri thức\",\"right\":false},{\"id\":\"answer_id_603615e1-29f5-46aa-b3af-6db6ba6a9d7d\",\"content\":\"Cầu cho năm mới tài lộc, phúc thọ đầy nhà\",\"right\":false},{\"id\":\"answer_id_9b602244-9f27-4ed0-8d46-2386eacca5ef\",\"content\":\"Tất cả đáp án trên\",\"right\":true}]}],\"timeStart\":1644595825000,\"timeEnd\":1644619020000,\"status\":2},{\"id\":\"topic_id_6a138b76-e6d9-4e08-adb3-61615f715cd6\",\"title\":\"Vì sao năm nay không có 30 Tết?\",\"questions\":[{\"id\":\"question_id_3cab01b5-5ad8-4adb-a448-c7f0a4f5c467\",\"content\":\"Năm dương lịch được tính dựa trên thời gian một vòng Trái Đất quay quanh?\",\"options\":[{\"id\":\"answer_id_64d8f69e-6dd7-48b0-8f5d-dd8557c16e56\",\"content\":\"Mặt Trời\",\"right\":true},{\"id\":\"answer_id_990c19c9-3a6a-4d4e-a648-337aa015efaf\",\"content\":\"Mặt Trăng\",\"right\":false},{\"id\":\"answer_id_0c1796d2-05fa-4084-bc95-f0fdebb75140\",\"content\":\"Sao Hỏa\",\"right\":false}]},{\"id\":\"question_id_d48cab30-f3ca-4c59-ab7b-e4d2a1d8ff6d\",\"content\":\"Vì sao có năm có 365, có năm có 366 ngày?\",\"options\":[{\"id\":\"answer_id_9c4cf566-ef49-4300-94d4-d041862eac1c\",\"content\":\"Năm đẹp nên thêm ngày\",\"right\":false},{\"id\":\"answer_id_850d001b-a036-490f-ac60-9512e8e0a7fe\",\"content\":\"Không theo quy tắc gì\",\"right\":false},{\"id\":\"answer_id_d9e26982-3c7c-457d-bf7d-20c5e6da4413\",\"content\":\"Thời gian mỗi năm thừa tạo thành\",\"right\":true}]},{\"id\":\"question_id_a7e53f02-99bd-44ca-8a08-2866d66e8096\",\"content\":\"Năm âm lịch được tính bằng chu kỳ tròn khuyết của?\",\"options\":[{\"id\":\"answer_id_328388fd-05e0-4655-821b-68ea664dc2ee\",\"content\":\"Mặt Trăng\",\"right\":true},{\"id\":\"answer_id_5276f4d9-e778-422c-8e38-fef27b11e13d\",\"content\":\"Mặt Trời\",\"right\":false}]}],\"timeStart\":1644676260000,\"timeEnd\":1644701460000,\"status\":1}]");
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
