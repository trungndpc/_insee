package vn.insee.retailer.bot.question.football;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import vn.insee.retailer.bot.Question;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.entity.football.PredictMatchBotEntity;
import vn.insee.retailer.webhook.zalo.UserSendMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;

import java.util.Arrays;

public class RePredictMatchQuestion extends Question {
    private static final Logger LOGGER = LogManager.getLogger(RePredictMatchQuestion.class);
    private static final String FORMAT = "Anh/chị đã đự đoán:\n" +
            "%s\n" +
            "Anh có muốn thay đổi?";
    private Boolean answer;
    private PredictMatchBotEntity predict;

    public RePredictMatchQuestion(JSONObject data) throws JsonProcessingException {
        super(data);
        this.answer = data.optBoolean("answer", false);
        this.predict = mapper.readValue(data.getJSONObject("predict").toString(), PredictMatchBotEntity.class);
    }

    public RePredictMatchQuestion(User user, PredictMatchBotEntity predict) {
        super(user);
        this.predict = predict;
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
        yesButton.title = "Có, Tôi muốn dự đoán lại";
        yesButton.payload = "Có";

        ZaloMessage.Attachment.Payload.Button noButton = new ZaloMessage.Attachment.Payload.Button();
        noButton.type = "oa.query.show";
        noButton.title = "Không";
        noButton.payload = "Không";

        payload.buttons = Arrays.asList(yesButton, noButton);

        ZaloMessage.Attachment attachment = new ZaloMessage.Attachment();
        attachment.type = "template";
        attachment.payload = payload;
        ZaloMessage message = ZaloMessage.toTextMessage(String.format(FORMAT, toTextResult()));
        message.attachment = attachment;
        return message;
    }

    private String toTextResult() {
        if (predict.getTeamWin() == 1) {
            return predict.getMatch().getTeamA()
                    + " thắng " + predict.getMatch().getTeamB();
//            + " với tỉ số: " + predict.getGoalTeamA() + " - " + predict.getGoalTeamB();
        }else if (predict.getTeamWin() == 2) {
            return predict.getMatch().getTeamB()
                    + " thắng " + predict.getMatch().getTeamA();
//            + " với tỉ số: " + predict.getGoalTeamB() + " - " + predict.getGoalTeamA();
        }else {
            return predict.getMatch().getTeamA()
                    + " hòa " + predict.getMatch().getTeamB();
//                    + " với tỉ số: " + predict.getGoalTeamA() + " - " + predict.getGoalTeamB();
        }
//
//        if (predict.getGoalTeamA() > predict.getGoalTeamB()) {
//            return predict.getMatch().getTeamA()
//                    + " thắng " + predict.getMatch().getTeamB();
////            + " với tỉ số: " + predict.getGoalTeamA() + " - " + predict.getGoalTeamB();
//        }else if (predict.getGoalTeamB() > predict.getGoalTeamA()) {
//            return predict.getMatch().getTeamB()
//                    + " thắng " + predict.getMatch().getTeamA();
////            + " với tỉ số: " + predict.getGoalTeamB() + " - " + predict.getGoalTeamA();
//        }else {
//            return predict.getMatch().getTeamA()
//                    + " hòa " + predict.getMatch().getTeamB();
////                    + " với tỉ số: " + predict.getGoalTeamA() + " - " + predict.getGoalTeamB();
//        }
    }

    public Boolean getAnswer() {
        return answer;
    }

    public PredictMatchBotEntity getPredict() {
        return predict;
    }
}
