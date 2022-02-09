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
import java.util.List;

public class LightingQuizGameRatioQuestion extends Question implements LQQuestion{
    private static final Logger LOGGER = LogManager.getLogger(LightingQuizGameRatioQuestion.class);
    private int promotionId;
    private String topicId;
    private String questionId;
    private String content;
    private String trueAns;
    private List<String> options;

    private String userAnswer;

    public LightingQuizGameRatioQuestion() {
        super();
    }

    public LightingQuizGameRatioQuestion(User user) {
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
    public boolean ans(ZaloWebhookMessage  msg) {
        UserSendMessage userSendMsg = (UserSendMessage) msg;
        this.userAnswer = userSendMsg.message.text;
        return BotUtil.isAcceptAns(this.userAnswer, options);
    }

    @Override
    public Object getUserAnswer() {
        return this.userAnswer;
    }

    private ZaloMessage buildMsg() {
        ZaloMessage.Attachment.Payload payload = new ZaloMessage.Attachment.Payload();
        payload.buttons = new ArrayList<>();
        for (String option: options) {
            ZaloMessage.Attachment.Payload.Button button = new ZaloMessage.Attachment.Payload.Button();
            button.type = "oa.query.show";
            button.title = option;
            button.payload = option;
            payload.buttons.add(button);
        }
        ZaloMessage.Attachment attachment = new ZaloMessage.Attachment();
        attachment.type = "template";
        attachment.payload = payload;
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(content);
        zaloMessage.attachment = attachment;
        return zaloMessage;
    }

    @Override
    public String getQuestionId() {
        return questionId;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTrueAns() {
        return trueAns;
    }

    public void setTrueAns(String trueAns) {
        this.trueAns = trueAns;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}


