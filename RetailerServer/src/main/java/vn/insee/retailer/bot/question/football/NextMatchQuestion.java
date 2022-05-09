package vn.insee.retailer.bot.question.football;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import vn.insee.retailer.bot.Question;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.entity.football.MatchBotEntity;
import vn.insee.retailer.webhook.zalo.UserSendMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;

import java.util.Arrays;

public class NextMatchQuestion extends Question {
    private static final Logger LOGGER = LogManager.getLogger(NextMatchQuestion.class);
    private static final String FORMAT = "Anh/Chị có muốn tham gia dự đoán kết quả của trận tiếp theo không?(%s - %s)";
    private Boolean answer;
    private MatchBotEntity match;

    public NextMatchQuestion(JSONObject data) throws JsonProcessingException {
        super(data);
        this.answer = data.optBoolean("answer", false);
        this.match = mapper.readValue(data.getJSONObject("match").toString(), MatchBotEntity.class);
    }

    public NextMatchQuestion(User user, MatchBotEntity match) {
        super(user);
        this.match = match;
    }

    @Override
    public boolean ask() {
        try{
            ZaloMessage message = build();
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
        String text = userSendMsg.message.text;
        if ("Có".equalsIgnoreCase(text)) {
            this.answer = true;
            return true;
        }
        if ("Không".equalsIgnoreCase(text)) {
            this.answer = false;
            return true;
        }
        return false;
    }

    @Override
    public Object getUserAnswer() {
        return this.answer;
    }

    private ZaloMessage build() {
        ZaloMessage.Attachment.Payload payload = new ZaloMessage.Attachment.Payload();

        ZaloMessage.Attachment.Payload.Button yesButton = new ZaloMessage.Attachment.Payload.Button();
        yesButton.type = "oa.query.show";
        yesButton.title = "Có";
        yesButton.payload = "Có";

        ZaloMessage.Attachment.Payload.Button noButton = new ZaloMessage.Attachment.Payload.Button();
        noButton.type = "oa.query.show";
        noButton.title = "Không";
        noButton.payload = "Không";

        payload.buttons = Arrays.asList(yesButton, noButton);

        ZaloMessage.Attachment attachment = new ZaloMessage.Attachment();
        attachment.type = "template";
        attachment.payload = payload;
        ZaloMessage message = ZaloMessage.toTextMessage(String.format(FORMAT, match.getTeamA(), match.getTeamB()));
        message.attachment = attachment;
        return message;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public MatchBotEntity getMatch() {
        return match;
    }
}
