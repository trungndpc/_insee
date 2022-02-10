package vn.insee.util;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {
    private static final Logger LOGGER = LogManager.getLogger(TimeUtil.class);

    public static int getTime(String str) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date parse = formatter.parse(str);
            long l = parse.getTime() / 1000 ;
            return (int) l;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return 0;
    }

    public static String toStringTime(ZonedDateTime timeStart) {
        try{
            return DateTimeFormatter.ofPattern("hh:mm - dd/MM").format(timeStart);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static String toStringHour(ZonedDateTime zonedDateTime) {
        try{
            return DateTimeFormatter.ofPattern("hh:mm").format(zonedDateTime).replace(":", "h");
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static String formatDuration(long millis) {
        return DurationFormatUtils.formatDuration(millis, "H giờ m phút s giây", true)
                .replaceAll("0 giờ", "")
                .replaceAll("0 phút", "")
                .replaceAll("0 giây", "")
                .replaceAll("giâ0", "giây")
                .trim();
    }

}
