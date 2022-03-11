package vn.insee.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constant {
    public static final String ADMIN_DOMAIN = "https://admin.insee.udev.com.vn";
    public static String ADMIN_DOMAIN_WITHOUT_PROTOCOL;
    public static final String CLIENT_DOMAIN = "https://cuahang.insee.udev.com.vn";
    public static String PREFIX_ADMIN_CONTROLLER;
    public static String PREFIX_CLIENT_CONTROLLER;
    public static String ADMIN_DOMAIN_VERSION;
    public static String CLIENT_DOMAIN_VERSION;
    public static long ZALO_APP_ID;
    public static String ZALO_SECRET_APP;
    public static String ZALO_OA_ACCESS_TOKEN;
    public static String ZALO_OA_REDIRECT_AUTHEN_ZALO;
    public static String ZALO_OA_REQUEST_OA_INFO;

    public static String CONTENT_RESPONSE_TO_REDIRECT = "REDIRECT";

    @Value("${domain.prefix.admin.controller}")
    public void setPrefixAdminController(String prefixAdminController) {
        PREFIX_ADMIN_CONTROLLER = prefixAdminController;
    }

    @Value("${domain.prefix.client.controller}")
    public void setPrefixClientController(String prefixClientController) {
        PREFIX_CLIENT_CONTROLLER = prefixClientController;
    }

    @Value("${domain.version.admin}")
    public void setAdminDomainVersion(String adminDomainVersion) {
        ADMIN_DOMAIN_VERSION = adminDomainVersion;
    }

    @Value("${domain.version.client}")
    public void setClientDomainVersion(String clientDomainVersion) {
        CLIENT_DOMAIN_VERSION = clientDomainVersion;
    }

    @Value("${zalo.authen.appid}")
    public void setZaloAppId(long zaloAppId) {
        ZALO_APP_ID = zaloAppId;
        ZALO_OA_REDIRECT_AUTHEN_ZALO = "https://oauth.zaloapp.com/v3/auth?app_id=" + zaloAppId + "&state=insee";
    }

    @Value("${zalo.authen.secret}")
    public void setZaloSecretApp(String zaloSecretApp) {
        ZALO_SECRET_APP = zaloSecretApp;
    }

    @Value("${zalo.oa.access_token}")
    public void setZaloOaAccessToken(String zaloOaAccessToken) {
        ZALO_OA_ACCESS_TOKEN = zaloOaAccessToken;
    }

    @Value("${domain.oa.zalo.get-profile}")
    public void setZaloOaRequestOaInfo(String zaloOaRequestOaInfo) {
        ZALO_OA_REQUEST_OA_INFO = zaloOaRequestOaInfo;
    }

}
