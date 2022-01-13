package vn.insee.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HttpUtil {

    public static String getCookie(String name, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return null;
        }
        for (Cookie co : cookies) {
            if (name.equals(co.getName())) {
                return co.getValue();
            }
        }
        return null;
    }

    public static String getFullDomain(HttpServletRequest request) {
        try{
            URL url = new URL(request.getRequestURL().toString());
            String host  = url.getHost();
            String scheme = url.getProtocol();
            int port = url.getPort();
            URI uri = new URI("https",null,host,port,null,null,null);
            return uri.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    public static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
