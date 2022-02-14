package vn.insee.util;

import java.util.UUID;

public class NoiseUtil {
    private static final String KEY = "insee";

    public static String noiseInt(int a) {
        return AES.encrypt(String.valueOf(a), KEY);
    }

    public static int de_noiseInt(String v) {
        String value = AES.decrypt(v, KEY);
        return Integer.parseInt(value);
    }

    public static String random() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static void main(String[] args) {
        System.out.println(NoiseUtil.noiseInt(10));
    }
}
