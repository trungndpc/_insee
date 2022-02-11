package vn.insee.admin.retailer.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.admin.retailer.wrapper.entity.ZaloMessage;


public class ApprovedStockPromotionMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger(ApprovedStockPromotionMessage.class);
    private String title;
    public ApprovedStockPromotionMessage(User user, String name) {
        super(user);
        this.title = name;
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
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage("Chúc mừng anh " + title + " hình ảnh tham gia chương trình của anh " +
                "đã được xác nhận. Chúng tối sẽ gửi phần quà cho anh chị trong thời gian tới.");
        return zaloMessage;
    }
}
