package vn.insee.admin.retailer.message.football;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.admin.retailer.message.Message;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.admin.retailer.wrapper.entity.ZaloMessage;

public class SuccessPredictMatchMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FORMAT = "Chúc mừng Anh/Chị đã dự đoán đúng kết quả của trận đấu giữa %s-%s.\n" +
            "Anh/Chị đã tích lũy được %d điểm.\n" +
            "Hãy tham gia dự đoán các trận tiếp theo để có cơ hội nhận phần quà hấp dẫn từ INSEE";
    private String teamOne;
    private String teamTwo;
    private int point;

    public SuccessPredictMatchMessage(User user, String teamOne, String teamTwo, int point) {
        super(user);
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.point = point;
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
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(String.format(FORMAT, teamOne, teamTwo, point));
        return zaloMessage;
    }
}
