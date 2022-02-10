package vn.insee.retailer.bot.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.common.Constant;
import vn.insee.retailer.bot.Message;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Time2StartGameMessage extends Message {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-M-yyyy hh:mm");
    private static final Logger LOGGER = LogManager.getLogger(Time2StartGameMessage.class);
    private String title;
    private int id;
    private long timeStart;

    public Time2StartGameMessage(User user, String title, int id, long timeStart) {
        super(user);
        this.title = title;
        this.id = id;
        this.timeStart = timeStart;
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
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(title + " sẽ được bắt đầu vào lúc: " + SIMPLE_DATE_FORMAT.format(new Date(timeStart)) );
        zaloMessage.attachment = attachment;
        return zaloMessage;
    }
}
