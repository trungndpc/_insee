package vn.insee.retailer.bot.question.football;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
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
    private static final String TEXT_QUESTION = "Anh/chị muốn dự đoán trận đấu nào dưới đây?";
    private static final String FORMAT_NAME_MATCH = "%s - %s";
    private List<MatchBotEntity> matches;
    private MatchBotEntity selectedMatch;

    public WhichMatchQuestion(JSONObject data) throws JsonProcessingException {
        super(data);
        this.matches = mapper.readValue(data.getJSONArray("matches").toString(), new TypeReference<List<MatchBotEntity>>() {
        });
        this.selectedMatch = mapper.readValue(data.get("selectedMatch").toString(), MatchBotEntity.class);
    }

    public WhichMatchQuestion(User user, List<MatchBotEntity> matches) {
        super(user);
        this.matches = matches;
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
        button.payload = btnPayload;
        payload.buttons.add(button);

        ZaloMessage.Attachment attachment = new ZaloMessage.Attachment();
        attachment.type = "template";
        attachment.payload = payload;
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(TEXT_QUESTION);
        zaloMessage.attachment = attachment;
        return zaloMessage;
    }

    public List<MatchBotEntity> getMatches() {
        return matches;
    }

    public MatchBotEntity getSelectedMatch() {
        return selectedMatch;
    }
}
