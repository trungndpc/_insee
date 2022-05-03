package vn.insee.retailer.bot.question.football;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import vn.insee.retailer.bot.Question;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.entity.football.MatchBotEntity;
import vn.insee.retailer.bot.entity.football.PredictMatchBotEntity;
import vn.insee.retailer.webhook.zalo.UserSendMessage;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhichTeamQuestion extends Question {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TEXT_QUESTION = "Theo anh chị đội nào sẽ dành chiến thắng?";
    private static final String FORMAT_TEAM_WIN = "%s thắng";
    private static final String HOA = "Hòa";
    private static final String REGEX = "(.*)(\\d{1,2})(.*)(\\d{1,2})(.*)";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private MatchBotEntity match;

    private String win;
//    private Integer goalA;
//    private Integer goalB;

    public WhichTeamQuestion(JSONObject data) throws JsonProcessingException {
        super(data);
//        this.goalA = data.optInt("goalA", -1);
//        this.goalB = data.optInt("goalB", -1);
        this.win = data.optString("win", null);
        this.match = mapper.readValue(data.getJSONObject("match").toString(), MatchBotEntity.class);
    }

    public WhichTeamQuestion(User user, MatchBotEntity match) {
        super(user);
        this.match = match;
    }

    @Override
    public boolean ask() {
        try {
            ZaloMessage message = buildMsg();
            ZaloService.INSTANCE.send(this.getUser().getFollowerId(), message);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean ans(ZaloWebhookMessage msg) {
        try {
            UserSendMessage userSendMsg = (UserSendMessage) msg;
            String text = userSendMsg.message.text;
            String TEAM_A_WIN = String.format(FORMAT_TEAM_WIN, match.getTeamA());
            String TEAM_B_WIN = String.format(FORMAT_TEAM_WIN, match.getTeamB());
            if (TEAM_A_WIN.equalsIgnoreCase(text)) {
                win = match.getTeamA();
//                ZaloService.INSTANCE.send(this.getUser().getFollowerId(), buildAskResult(win));
                return true;
            }

            if (TEAM_B_WIN.equalsIgnoreCase(text)) {
                win = match.getTeamB();
//                ZaloService.INSTANCE.send(this.getUser().getFollowerId(), buildAskResult(win));
                return true;
            }

            if (HOA.equalsIgnoreCase(text)) {
                win = HOA;
//                ZaloService.INSTANCE.send(this.getUser().getFollowerId(), buildAskResult());
                return true;
            }

//            if (win != null && !win.isEmpty()) {
//                Matcher matcher = PATTERN.matcher(text);
//                if (matcher.find()) {
//                    int s1 = Integer.parseInt(matcher.group(2));
//                    int s2 = Integer.parseInt(matcher.group(4));
//                    if (win.equalsIgnoreCase(match.getTeamA())) {
//                        goalA = s1 > s2 ? s1 : s2;
//                        goalB = s1 > s2 ? s2 : s1;
//                    }else if (win.equalsIgnoreCase(match.getTeamB())) {
//                        goalA = s1 > s2 ? s2 : s1;
//                        goalB = s1 > s2 ? s1 : s2;
//                    }else {
//                        goalA = s1;
//                        goalB = s2;
//                    }
//                    return true;
//                }
//            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Object getUserAnswer() {
        if (win.equals(HOA)) {
            return new PredictMatchBotEntity(match, 0);
        }
        if (win.equals(match.getTeamA())) {
            return new PredictMatchBotEntity(match, 1);
        }
        if (win.equals(match.getTeamB())) {
            return new PredictMatchBotEntity(match, 2);
        }
        return "";
    }


    private ZaloMessage buildMsg() {
        ZaloMessage.Attachment.Payload payload = new ZaloMessage.Attachment.Payload();
        payload.buttons = new ArrayList<>();

        ZaloMessage.Attachment.Payload.Button btnTeamA = new ZaloMessage.Attachment.Payload.Button();
        btnTeamA.type = "oa.query.show";
        String teamAWin = String.format(FORMAT_TEAM_WIN, match.getTeamA());
        btnTeamA.title = teamAWin;
        btnTeamA.payload = teamAWin;
        payload.buttons.add(btnTeamA);

        ZaloMessage.Attachment.Payload.Button btnTeamB = new ZaloMessage.Attachment.Payload.Button();
        btnTeamB.type = "oa.query.show";
        String teamBWin = String.format(FORMAT_TEAM_WIN, match.getTeamB());
        btnTeamB.title = teamBWin;
        btnTeamB.payload = teamBWin;
        payload.buttons.add(btnTeamB);

        ZaloMessage.Attachment.Payload.Button hoa = new ZaloMessage.Attachment.Payload.Button();
        hoa.type = "oa.query.show";
        hoa.title = HOA;
        hoa.payload = HOA;
        payload.buttons.add(hoa);

        ZaloMessage.Attachment attachment = new ZaloMessage.Attachment();
        attachment.type = "template";
        attachment.payload = payload;
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(TEXT_QUESTION);
        zaloMessage.attachment = attachment;
        return zaloMessage;
    }

    private ZaloMessage buildAskResult(String win) {
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage(win + " thắng với tỉ số bao nhiêu?");
        return zaloMessage;
    }

    private ZaloMessage buildAskResult() {
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage("hòa với tỉ số bao nhiêu ạ?");
        return zaloMessage;
    }

    public MatchBotEntity getMatch() {
        return match;
    }

    public String getWin() {
        return win;
    }

//    public Integer getGoalA() {
//        return goalA;
//    }
//
//    public void setGoalA(Integer goalA) {
//        this.goalA = goalA;
//    }
//
//    public Integer getGoalB() {
//        return goalB;
//    }
//
//    public void setGoalB(Integer goalB) {
//        this.goalB = goalB;
//    }


}
