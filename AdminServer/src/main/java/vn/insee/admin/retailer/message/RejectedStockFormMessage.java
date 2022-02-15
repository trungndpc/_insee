package vn.insee.admin.retailer.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.admin.retailer.wrapper.entity.ZaloMessage;


public class RejectedStockFormMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger(RejectedStockFormMessage.class);
    private String note;
    public RejectedStockFormMessage(User user, String note) {
        super(user);
        this.note = note;
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
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage("Đơn tham gia khuyến mãi của bạn không được duyệt!" +( note != null ?  (" Lý do: " + note) : ("")));
        return zaloMessage;
    }
}
