package vn.insee.util;

public class ReferralUtil {
    private static final int SALT = 197;
    public static Integer gen(int id) {
        return id + SALT;
    }

    public static Integer code2UserId(String code) {
        try {
            return Integer.parseInt(code) - SALT;
        }catch (Exception e) {

        }
        return 0;
    }
}
