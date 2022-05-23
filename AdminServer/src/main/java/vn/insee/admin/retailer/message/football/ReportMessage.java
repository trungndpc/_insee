package vn.insee.admin.retailer.message.football;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.admin.retailer.message.Message;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.admin.retailer.wrapper.entity.ZaloMessage;

public class ReportMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FORMAT = "Chúc mừng đội tuyển Việt Nam đã xuất sắc Đương đầu thách thức - Giữ vững ngôi vương\n" +
            "Anh/Chị đã dự đoán đúng %d/%d trận. Số điểm hiện tại của Anh/Chị là %d điểm.\n" +
            "Hãy cùng chờ đón kết quả bảng xếp hạng chung cuộc của INSEE nhé.";

    private int point;
    private int total;
    public ReportMessage(User user, int point, int total) {
        super(user);
        this.point = point;
        this.total = total;
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
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(String.format(FORMAT, point, total, point));
        return zaloMessage;
    }
}
