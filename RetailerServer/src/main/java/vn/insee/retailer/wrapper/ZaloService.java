package vn.insee.retailer.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import vn.insee.common.Constant;
import vn.insee.retailer.common.AppCommon;
import vn.insee.retailer.wrapper.entity.ZaloUserEntity;

import java.nio.charset.StandardCharsets;


public class ZaloService {
    public static final ZaloService INSTANCE = new ZaloService();
    private static final Logger LOGGER = LogManager.getLogger(ZaloService.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String GET_ACCESS_TOKEN_URL = "https://oauth.zaloapp.com/v3/access_token?app_id={1}&app_secret={2}&code={3}";
    private static final String GET_USER_INFO = "https://graph.zalo.me/v2.0/me?fields=id,name,picture,birthday,gender&access_token=";
    private static final String END_POINT = "https://openapi.zalo.me/v2.0/oa/message?access_token={1}";

    public ZaloService() {
        this.restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        this.objectMapper = new ObjectMapper();
    }

    public String getAccessToken(String oauthCode) throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(GET_ACCESS_TOKEN_URL, String.class,
                AppCommon.INSTANCE.getZaloAppId(),
                AppCommon.INSTANCE.getSecretZaloApp(), oauthCode);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception(responseEntity.getBody());
        }
        JSONObject json = new JSONObject(responseEntity.getBody());
        return  json.getString("access_token");
    }

    public ZaloUserEntity getUserInfo(String accessToken) throws Exception {
        String url = new StringBuffer(GET_USER_INFO).append(accessToken).toString();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception(responseEntity.getBody());
        }
        JSONObject json = new JSONObject(responseEntity.getBody());
        ZaloUserEntity entity = new ZaloUserEntity();
        entity.setId(json.getString("id"));
        entity.setName(json.getString("name"));
        entity.setGender(json.optString("gender"));
        entity.setBirthday(json.getString("birthday"));
        entity.setAvatar(json.getJSONObject("picture").getJSONObject("data").getString("url"));
        return entity;
    }

}
