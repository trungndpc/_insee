package vn.insee.admin.retailer.message.football;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.admin.retailer.message.Message;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.admin.retailer.wrapper.entity.ZaloMessage;

public class SuccessGroupStageMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FORMAT = "Chúc mừng anh/chị đã dự đoán đúng %d/20 trận của vòng bảng Seagame 31.\n" +
            "Anh/chị đã tích lũy được %d điểm.\n" +
            "Cùng đón chờ và tham gia dự đoán các trận tiếp theo để có cơ hội nhận phần quà hấp dẫn từ INSEE.";

    private int point;
    public SuccessGroupStageMessage(User user, int point) {
        super(user);
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
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(String.format(FORMAT, point, point));
        return zaloMessage;
    }
}
