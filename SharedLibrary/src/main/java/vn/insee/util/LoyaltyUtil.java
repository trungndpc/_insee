package vn.insee.util;

public class LoyaltyUtil {

    public static int getNextTon(int ton) {
        int t = ton / 1000;
        if (t < 100) {
            return 100;
        }
        if (t < 200) {
            return 200;
        }
        if (t < 350) {
            return 350;
        }
        if (t < 500) {
            return 500;
        }
        return 1000;
    }
}
