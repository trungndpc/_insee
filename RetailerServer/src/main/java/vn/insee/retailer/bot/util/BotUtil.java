package vn.insee.retailer.bot.util;

import java.util.List;
import java.util.Optional;

public class BotUtil {
    public static boolean isEquivalent(String text, String ans) {
        return ans.equalsIgnoreCase(text);
    }

    public static boolean isAcceptAns(String text, List<String> options) {
        return options.stream().filter(o -> isEquivalent(text, o)).findAny().isPresent();
    }

    public static String String (String text, List<String> options) {
        Optional<String> any = options.stream().filter(o -> isEquivalent(text, o)).findAny();
        return any.get();
    }
}
