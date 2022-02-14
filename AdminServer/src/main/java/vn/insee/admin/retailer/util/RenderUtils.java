package vn.insee.admin.retailer.util;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import vn.insee.admin.retailer.common.AppCommon;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class RenderUtils {
    private static final ConcurrentHashMap<String, String> HTMLs = new ConcurrentHashMap<>();

    public static String render(String path) throws IOException {
        String cache = HTMLs.getOrDefault(path, null);
        if (cache == null) {
            InputStream inputStream = new ClassPathResource("html/" + path).getInputStream();
            cache = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
            HTMLs.put(path, cache);
        }
        return cache;
    }
}
