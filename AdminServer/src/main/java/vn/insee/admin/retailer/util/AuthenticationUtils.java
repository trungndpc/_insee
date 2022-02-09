//package retailer.util;
//
//import org.springframework.http.ResponseCookie;
//import org.springframework.security.core.Authentication;
//import retailer.security.InseeUserDetail;
//import vn.insee.jpa.entity.UserEntity;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.UUID;
//
//public class AuthenticationUtils {
//
//    public static UserEntity getAuthUser(Authentication authentication) {
//        if (authentication == null) {
//            return null;
//        }
//        InseeUserDetail userDetails = (InseeUserDetail) authentication.getPrincipal();
//        return userDetails.getUser();
//    }
//
//    public static String randomUUID() {
//        UUID uuid = UUID.randomUUID();
//        return uuid.toString();
//    }
//
//    public static void writeCookie(String name, String value, HttpServletResponse resp) {
//        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from(name, value)
//                .path("/")
//                .httpOnly(Boolean.TRUE)
//                .secure(Boolean.TRUE)
//                .sameSite("None")
//                .maxAge(10 * 60 * 1000);
//        String strCookie = cookieBuilder.build().toString();
//        resp.addHeader("Set-Cookie", strCookie);
//    }
//}
