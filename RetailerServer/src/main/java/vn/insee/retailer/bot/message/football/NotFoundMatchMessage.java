package vn.insee.retailer.bot.message.football;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.retailer.bot.Message;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;

public class NotFoundMatchMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger(NotFoundMatchMessage.class);

    public NotFoundMatchMessage(User user) {
        super(user);
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
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage("Xin lỗi! Không có trận đấu nào sắp diễn ra");
        return zaloMessage;
    }
}
