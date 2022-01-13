package vn.insee.retailer.util;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import vn.insee.retailer.common.AppCommon;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class RenderUtils {
    private static final ConcurrentHashMap<String, String> HTMLs = new ConcurrentHashMap<>();
    public static String render(String path, String field, String value) throws IOException {
        String html = render(path);
        html.replaceAll("\\{\\{"+field+"\\}\\}", value);
        return html;
    }

    public static String render(String path) throws IOException {
        String cache = HTMLs.getOrDefault(path, null);
        if (cache == null) {
            InputStream inputStream = new ClassPathResource("webapp/" + path + ".html").getInputStream();
            cache = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
            cache = cache.replaceAll("\\{\\{domain\\}\\}", AppCommon.INSTANCE.getDomain());
            cache = cache.replaceAll("\\{\\{version\\}\\}",AppCommon.INSTANCE.getVersion());
        }
        return cache;
    }
}
