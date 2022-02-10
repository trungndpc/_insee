package vn.insee.retailer.bot.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.retailer.bot.Message;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;
import vn.insee.util.TimeUtil;

public class CompleteGameMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger(CompleteGameMessage.class);
    private int numTrueAns;
    private int numTotalQuestion;
    private long duration;

    public CompleteGameMessage(User user, int numTrueAns, int numTotalQuestion, long duration) {
        super(user);
        this.numTrueAns = numTrueAns;
        this.numTotalQuestion = numTotalQuestion;
        this.duration = duration;
    }

    @Override
    public boolean send() {
        try{
            ZaloMessage message = buildMsg();
            ZaloService.INSTANCE.send(getUser().getFollowerId(), message);
            return true;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    private ZaloMessage buildMsg() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----Chúc mừng anh/chị----");
        stringBuilder.append("\n");
        stringBuilder.append("Đã hoàn thành xong phần thi của mình với kết quả: " + numTrueAns + "/" + numTotalQuestion + " câu đúng, " +
                "trong khoảng thời gian: " + toDuration(duration));
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(stringBuilder.toString());
        return zaloMessage;
    }

    private String toDuration(long duration) {
        return TimeUtil.formatDuration(duration);
    }
}
