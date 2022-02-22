package vn.insee.retailer.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.security.InseeUserDetail;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class AuthenticationUtils {
    private static final Logger LOGGER = LogManager.getLogger();
    public static UserEntity getAuthUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        if (authentication.getPrincipal() instanceof InseeUserDetail) {
            InseeUserDetail userDetails = (InseeUserDetail) authentication.getPrincipal();
            return userDetails.getUser();
        }
        return null;
    }

    public static String randomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static void writeCookie(String name, String value, HttpServletResponse resp) {
        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from(name, value)
                .path("/")
                .httpOnly(Boolean.TRUE)
                .secure(Boolean.TRUE)
                .sameSite("None")
                .maxAge(10 * 60 * 1000);
        String strCookie = cookieBuilder.build().toString();
        resp.addHeader("Set-Cookie", strCookie);
    }
}
