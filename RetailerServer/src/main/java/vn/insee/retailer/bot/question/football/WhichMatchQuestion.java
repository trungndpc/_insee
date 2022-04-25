package vn.insee.retailer.bot.question.football;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.common.Constant;
import vn.insee.retailer.bot.Question;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.entity.football.MatchBotEntity;
import vn.insee.retailer.bot.util.BotUtil;
import vn.insee.retailer.webhook.zalo.UserSendMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;

import java.util.ArrayList;
import java.util.List;

public class WhichMatchQuestion extends Question{
    private static final Logger LOGGER = LogManager.getLogger(WhichMatchQuestion.class);
    private static final String TEXT_QUESTION = "";
    private static final String FORMAT_NAME_MATCH = "%s - %s";
    private List<MatchBotEntity> matches;

    public WhichMatchQuestion(User user, String id, List<MatchBotEntity> matches) {
        super(user, id);
        this.matches = matches;
    }

    private MatchBotEntity selectedMatch;

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
        String text = userSendMsg.message.text;
        for (MatchBotEntity match : matches) {
            String name = String.format(FORMAT_NAME_MATCH, match.getTeamA(), match.getTeamB());
            if (BotUtil.isEquivalent(text, name)) {
                this.selectedMatch = match;
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getUserAnswer() {
        return this.selectedMatch;
    }

    private ZaloMessage buildMsg() {
        ZaloMessage.Attachment.Payload payload = new ZaloMessage.Attachment.Payload();
        payload.buttons = new ArrayList<>();

        for (MatchBotEntity match : matches) {
            ZaloMessage.Attachment.Payload.Button button = new ZaloMessage.Attachment.Payload.Button();
            button.type = "oa.query.show";
            String nameMatch = String.format(FORMAT_NAME_MATCH, match.getTeamA(), match.getTeamB());
            button.title = nameMatch;
            button.payload = nameMatch;
            payload.buttons.add(button);
        }

        ZaloMessage.Attachment.Payload.Button button = new ZaloMessage.Attachment.Payload.Button();
        button.type = "oa.open.url";
        button.title = "Thể lệ chương trình";
        ZaloMessage.Attachment.Payload.Button.ButtonPayload btnPayload = new ZaloMessage.Attachment.Payload.Button.ButtonPayload();
        btnPayload.url = Constant.CLIENT_DOMAIN + "/thach-thuc-phen-man";
        payload.buttons.add(button);

        ZaloMessage.Attachment attachment = new ZaloMessage.Attachment();
        attachment.type = "template";
        attachment.payload = payload;
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(TEXT_QUESTION);
        zaloMessage.attachment = attachment;
        return zaloMessage;
    }

}
