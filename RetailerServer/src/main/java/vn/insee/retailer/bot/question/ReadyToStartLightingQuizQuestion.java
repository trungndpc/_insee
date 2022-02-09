package vn.insee.retailer.bot.question;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.retailer.bot.Question;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.util.BotUtil;
import vn.insee.retailer.webhook.zalo.UserSendMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;

import java.util.ArrayList;

public class ReadyToStartLightingQuizQuestion extends Question implements LQQuestion{
    private static final Logger LOGGER = LogManager.getLogger(ReadyToStartLightingQuizQuestion.class);
    private static final String QUESTION_ID = "START";
    private String userAnswer;

    public ReadyToStartLightingQuizQuestion(User user) {
        super(user);
    }

    @Override
    public boolean ask() {
        try{
            ZaloMessage message = buildMsg();
            ZaloService.INSTANCE.send(this.getUser().getFollowerId(), message);
            return true;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean ans(ZaloWebhookMessage msg) {
        UserSendMessage userSendMsg = (UserSendMessage) msg;
        this.userAnswer = userSendMsg.message.text;
        return BotUtil.isEquivalent(this.userAnswer, "Bắt đầu");
    }

    @Override
    public Object getUserAnswer() {
        return this.userAnswer;
    }

    @Override
    public String getQuestionId() {
        return QUESTION_ID;
    }

    private ZaloMessage buildMsg() {
        ZaloMessage.Attachment.Payload payload = new ZaloMessage.Attachment.Payload();
        payload.buttons = new ArrayList<>();
        ZaloMessage.Attachment.Payload.Button button = new ZaloMessage.Attachment.Payload.Button();
        button.type = "oa.query.show";
        button.title = "Bắt đầu";
        button.payload = "Bắt đầu";
        payload.buttons.add(button);

        ZaloMessage.Attachment attachment = new ZaloMessage.Attachment();
        attachment.type = "template";
        attachment.payload = payload;
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage("Anh/Chị sẵn sàng để bắt đầu?");
        zaloMessage.attachment = attachment;
        return zaloMessage;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
