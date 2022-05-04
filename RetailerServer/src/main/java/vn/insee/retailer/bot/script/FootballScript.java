package vn.insee.retailer.bot.script;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import vn.insee.common.status.StatusForm;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;
import vn.insee.retailer.bot.PredictFootballSession;
import vn.insee.retailer.bot.Question;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.entity.football.MatchBotEntity;
import vn.insee.retailer.bot.entity.football.PredictMatchBotEntity;
import vn.insee.retailer.bot.message.football.CompletePredictMessage;
import vn.insee.retailer.bot.message.football.NotFoundMatchMessage;
import vn.insee.retailer.bot.question.football.RePredictMatchQuestion;
import vn.insee.retailer.bot.question.football.SummaryPredictMatchQuestion;
import vn.insee.retailer.bot.question.football.WhichMatchQuestion;
import vn.insee.retailer.bot.question.football.WhichTeamQuestion;
import vn.insee.retailer.service.MatchFootBallService;
import vn.insee.retailer.service.PredictFootballMatchService;
import vn.insee.retailer.util.BeanUtil;
import vn.insee.retailer.webhook.WebhookSessionManager;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloMessage;

import java.util.List;
import java.util.stream.Collectors;

public class FootballScript {
    private static final Logger LOGGER = LogManager.getLogger();
    private final static MatchFootBallService MATCH_FOOTBALL_SERVICE = BeanUtil.getBean(MatchFootBallService.class);
    private final static PredictFootballMatchService PREDICT_FOOTBALL_MATCH_SERVICE = BeanUtil.getBean(PredictFootballMatchService.class);
    private final static WebhookSessionManager WEBHOOK_SESSION_MANAGER = BeanUtil.getBean(WebhookSessionManager.class);
    private PredictFootballSession session;
    private User user;

    private ObjectMapper objectMapper = new ObjectMapper();

    public FootballScript(User user) throws JsonProcessingException {
        this.user = user;
        initSession(user);
    }

    public void process(ZaloWebhookMessage msg) throws Exception {
        String waitingQuestionId = session.getWaitingQuestionId();
        if (waitingQuestionId != null) {
            Question question = initWaitingQuestion(waitingQuestionId);
            if (question == null) {
                throw new Exception("can not init waiting question | " + waitingQuestionId);
            }
            boolean isAccept = question.ans(msg);
            //store session
            String jsonQuestion = this.objectMapper.writeValueAsString(question);
            PredictFootballSession.PredictFootballSS predictFootballSS = new PredictFootballSession.PredictFootballSS(question.getId(),
                    question.getClass(), new JSONObject(jsonQuestion));
            this.session.putQuestion(question.getId(), predictFootballSS);
            this.session.setWaitingQuestionId(question.getId());
            WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);
            if (isAccept) {
                PredictFootballSession.PredictFootballSS questionSS = null;
                if (question instanceof WhichMatchQuestion) {
                    WhichMatchQuestion whichMatchQuestion = (WhichMatchQuestion) question;
                    MatchBotEntity match = (MatchBotEntity) whichMatchQuestion.getUserAnswer();
                    this.session.setMatch(match);

                    PredictMatchFootballFormEntity matchEntity = PREDICT_FOOTBALL_MATCH_SERVICE.findByUserIdAndMatchId(user.getUid(), match.getId());
                    if (matchEntity == null) {
                        //ask which team
                        WhichTeamQuestion whichTeamQuestion = new WhichTeamQuestion(user, match);
                        if (whichTeamQuestion.ask()) {
                            questionSS = new PredictFootballSession.PredictFootballSS(whichTeamQuestion.getId(),
                                    whichTeamQuestion.getClass(), new JSONObject(this.objectMapper.writeValueAsString(whichTeamQuestion)));
                        }
                    } else {
                        PredictMatchBotEntity predictMatchBot = new PredictMatchBotEntity(match, matchEntity.getTeamWin());
                        predictMatchBot.setId(matchEntity.getId());
                        this.session.setPredictId(matchEntity.getId());
                        //ask edit
                        RePredictMatchQuestion rePredictMatchQuestion = new RePredictMatchQuestion(user, predictMatchBot);
                        if (rePredictMatchQuestion.ask()) {
                            questionSS = new PredictFootballSession.PredictFootballSS(rePredictMatchQuestion.getId(),
                                    rePredictMatchQuestion.getClass(), new JSONObject(this.objectMapper.writeValueAsString(rePredictMatchQuestion)));
                        }
                    }
                } else if (question instanceof RePredictMatchQuestion) {
                    RePredictMatchQuestion rePredictMatchQuestion = (RePredictMatchQuestion) question;
                    Boolean isOk = (Boolean) rePredictMatchQuestion.getUserAnswer();
                    if (isOk) {
                        //ask which team
                        WhichTeamQuestion whichTeamQuestion = new WhichTeamQuestion(user, this.session.getMatch());
                        if (whichTeamQuestion.ask()) {
                            questionSS = new PredictFootballSession.PredictFootballSS(whichTeamQuestion.getId(),
                                    whichTeamQuestion.getClass(), new JSONObject(this.objectMapper.writeValueAsString(whichTeamQuestion)));
                        }
                    } else {
                        ZaloService.INSTANCE.send(user.getFollowerId(), ZaloMessage.toTextMessage("Dạ!"));
                    }
                } else if (question instanceof WhichTeamQuestion) {
                    WhichTeamQuestion whichTeamQuestion = (WhichTeamQuestion) question;
                    PredictMatchBotEntity predictMatchBot = (PredictMatchBotEntity) whichTeamQuestion.getUserAnswer();
                    this.session.setPredict(predictMatchBot);

                    //ask which summary
                    SummaryPredictMatchQuestion summaryPredictMatchQuestion = new SummaryPredictMatchQuestion(user, predictMatchBot);
                    if (summaryPredictMatchQuestion.ask()) {
                        questionSS = new PredictFootballSession.PredictFootballSS(summaryPredictMatchQuestion.getId(),
                                summaryPredictMatchQuestion.getClass(), new JSONObject(this.objectMapper.writeValueAsString(summaryPredictMatchQuestion)));
                    }
                } else if (question instanceof SummaryPredictMatchQuestion) {
                    SummaryPredictMatchQuestion summaryPredictMatchQuestion = (SummaryPredictMatchQuestion) question;
                    Boolean isOK = (Boolean) summaryPredictMatchQuestion.getUserAnswer();
                    if (isOK) {
                        complete();
                        CompletePredictMessage completePredictMessage = new CompletePredictMessage(user);
                        completePredictMessage.send();
                        WEBHOOK_SESSION_MANAGER.clearSession(user.getUid());
                    } else {
                        questionSS = this.session.getQuestion(WhichTeamQuestion.class);
                    }
                }
                if (questionSS != null) {
                    this.session.putQuestion(question.getId(), questionSS);
                    WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);
                }
            }
        }
    }

    private void complete() {
        PredictMatchBotEntity predict = this.session.getPredict();
        PredictMatchFootballFormEntity entity = new PredictMatchFootballFormEntity();
        entity.setId(this.session.getPredictId());
        entity.setUserId(user.getUid());
        entity.setMatchId(predict.getMatch().getId());
        entity.setSeason(this.session.getSeason());
        entity.setPromotionId(this.session.getPromotionId());
        entity.setTeamWin(predict.getTeamWin());
        entity.setStatus(StatusForm.INIT);
        PREDICT_FOOTBALL_MATCH_SERVICE.createOrSave(entity);
    }

    public void start(PredictFootballPromotionEntity promotionEntity) throws JsonProcessingException {
        List<MatchFootballEntity> top5MostRecent =
                MATCH_FOOTBALL_SERVICE.findTop5MostRecent(promotionEntity.getSeason());
        LOGGER.error("X: " + top5MostRecent.size());
        if (top5MostRecent == null || top5MostRecent.isEmpty()) {
            new NotFoundMatchMessage(user).send();
            return;
        }
        List<MatchBotEntity> matchBotEntityList = top5MostRecent.stream().map(entity -> {
            MatchBotEntity matchBotEntity = new MatchBotEntity();
            matchBotEntity.setId(entity.getId());
            matchBotEntity.setTeamA(entity.getTeamOne());
            matchBotEntity.setTeamB(entity.getTeamTwo());
            return matchBotEntity;
        }).collect(Collectors.toList());
        WhichMatchQuestion whichMatchQuestion = new WhichMatchQuestion(user, matchBotEntityList);
        whichMatchQuestion.ask();
        this.session.setWaitingQuestionId(whichMatchQuestion.getId());
        JSONObject json = new JSONObject(this.objectMapper.writeValueAsString(whichMatchQuestion));
        this.session.putQuestion(whichMatchQuestion.getId(), new PredictFootballSession.PredictFootballSS(whichMatchQuestion.getId(),
                WhichMatchQuestion.class, json));
        this.session.setPromotionId(promotionEntity.getId());
        this.session.setTimeStart(System.currentTimeMillis());
        this.session.setSeason(promotionEntity.getSeason());
        WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);
        return;
    }

    public void start(PredictFootballPromotionEntity promotionEntity, MatchFootballEntity match) throws JsonProcessingException {
        MatchBotEntity matchBotEntity = new MatchBotEntity();
        matchBotEntity.setId(match.getId());
        matchBotEntity.setTeamA(match.getTeamOne());
        matchBotEntity.setTeamB(match.getTeamTwo());
        WhichTeamQuestion whichTeamQuestion = new WhichTeamQuestion(user, matchBotEntity);
        whichTeamQuestion.ask();
        this.session.setWaitingQuestionId(whichTeamQuestion.getId());
        JSONObject json = new JSONObject(this.objectMapper.writeValueAsString(whichTeamQuestion));
        this.session.putQuestion(whichTeamQuestion.getId(), new PredictFootballSession.PredictFootballSS(whichTeamQuestion.getId(),
                WhichTeamQuestion.class, json));
        this.session.setPromotionId(promotionEntity.getId());
        this.session.setTimeStart(System.currentTimeMillis());
        this.session.setSeason(promotionEntity.getSeason());
        WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);
        return;
    }

    private Question initWaitingQuestion(String id) throws JsonProcessingException {
        PredictFootballSession.PredictFootballSS predictFootballSS = session.getQuestions().get(id);
        switch (predictFootballSS.getZclass().getSimpleName()) {
            case "WhichMatchQuestion":
                return new WhichMatchQuestion(predictFootballSS.getJson());
            case "WhichTeamQuestion":
                return new WhichTeamQuestion(predictFootballSS.getJson());
            case "SummaryPredictMatchQuestion":
                return new SummaryPredictMatchQuestion(predictFootballSS.getJson());
            case "RePredictMatchQuestion":
                return new RePredictMatchQuestion(predictFootballSS.getJson());
        }
        return null;
    }


    private void initSession(User user) {
        Object currentSession = WEBHOOK_SESSION_MANAGER.getCurrentSession(user.getUid());
        if (currentSession == null || !(currentSession instanceof PredictFootballSession)) {
            this.session = new PredictFootballSession();
        } else {
            this.session = (PredictFootballSession) currentSession;
        }

        if (this.session.getPromotionId() != 0 && this.session.getMatch() != null) {
            MatchBotEntity match = this.session.getMatch();
            //update latest match
            MatchFootballEntity matchEntity = MATCH_FOOTBALL_SERVICE.get(match.getId());
            match.setId(matchEntity.getId());
            match.setTeamA(matchEntity.getTeamOne());
            match.setTeamB(matchEntity.getTeamTwo());
            this.session.setMatch(match);


            //update latest predict
            PredictMatchFootballFormEntity predict = PREDICT_FOOTBALL_MATCH_SERVICE.findByUserIdAndMatchId(user.getUid(), match.getId());
            if (predict != null && this.session.getPredict() == null) {
                PredictMatchBotEntity predictMatchBot = new PredictMatchBotEntity(match, predict.getTeamWin());
                this.session.setPredict(predictMatchBot);
            }
        }
        WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);
    }
}
