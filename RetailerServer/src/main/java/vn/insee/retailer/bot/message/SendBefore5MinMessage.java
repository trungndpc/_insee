package vn.insee.retailer.bot.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.common.Constant;
import vn.insee.retailer.bot.Message;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;

import java.util.ArrayList;

public class SendBefore5MinMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger(SendBefore5MinMessage.class);
    private String title;
    private int id;

    public SendBefore5MinMessage(User user, String title, int id) {
        super(user);
        this.title = title;
        this.id = id;
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
        ZaloMessage.Attachment.Payload payload = new ZaloMessage.Attachment.Payload();
        payload.buttons = new ArrayList<>();
        ZaloMessage.Attachment.Payload.Button button = new ZaloMessage.Attachment.Payload.Button();
        button.type = "oa.open.url";
        button.title = "Thể lệ chương trình";
        ZaloMessage.Attachment.Payload.Button.ButtonPayload buttonPayload = new ZaloMessage.Attachment.Payload.Button.ButtonPayload();
        buttonPayload.url = Constant.CLIENT_DOMAIN + "/khuyen-mai/" + id;
        button.payload = buttonPayload;
        payload.buttons.add(button);

        ZaloMessage.Attachment attachment = new ZaloMessage.Attachment();
        attachment.type = "template";
        attachment.payload = payload;
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage("Còn 5 phút nữa để bắt đầu " + title);
        zaloMessage.attachment = attachment;
        return zaloMessage;
    }
}
