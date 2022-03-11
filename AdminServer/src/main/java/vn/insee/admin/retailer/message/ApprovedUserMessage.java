package vn.insee.admin.retailer.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.admin.retailer.wrapper.entity.ZaloMessage;


public class ApprovedUserMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger(ApprovedUserMessage.class);
    private String title;
    public ApprovedUserMessage(User user, String name) {
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
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage("Chúc mừng anh " + title + " đã được xác nhận là khách hàng của INSEE. " +
                "Giờ đây anh chị có thể tham gia các chương trình khuyến mãi độc quyền của INSEE");
        return zaloMessage;
    }
}
