package vn.insee.retailer.bot.script;

import com.fasterxml.jackson.core.JsonProcessingException;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.retailer.bot.PredictFootballSession;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.entity.football.MatchBotEntity;
import vn.insee.retailer.bot.entity.football.PredictMatchBotEntity;
import vn.insee.retailer.service.MatchFootBallService;
import vn.insee.retailer.service.PredictFootballMatchService;
import vn.insee.retailer.util.BeanUtil;
import vn.insee.retailer.webhook.WebhookSessionManager;

public class FootballScript {
    private final static MatchFootBallService MATCH_FOOTBALL_SERVICE = BeanUtil.getBean(MatchFootBallService.class);
    private final static PredictFootballMatchService PREDICT_FOOTBALL_MATCH_SERVICE = BeanUtil.getBean(PredictFootballMatchService.class);
    private final static WebhookSessionManager WEBHOOK_SESSION_MANAGER = BeanUtil.getBean(WebhookSessionManager.class);
    private PredictFootballSession session;
    private User user;

    public FootballScript(User user) throws JsonProcessingException {
        this.user = user;
        initSession(user);
    }


    private void initSession(User user) {
        Object currentSession = WEBHOOK_SESSION_MANAGER.getCurrentSession(user.getUid());
        if (currentSession == null || !(currentSession instanceof PredictFootballSession)) {
            this.session = new PredictFootballSession();
        }else {
            this.session = (PredictFootballSession) currentSession;
        }

        if (this.session.getPromotionId() != 0 && this.session.getMatch() != null) {
            MatchBotEntity match = this.session.getMatch();
            //update latest match
            MatchFootballEntity matchEntity  = MATCH_FOOTBALL_SERVICE.get(match.getId());
            match.setId(matchEntity.getId());
            match.setTeamA(matchEntity.getTeamOne());
            match.setTeamB(matchEntity.getTeamTwo());
            this.session.setMatch(match);


            //update latest predict
            PredictMatchFootballFormEntity predict = PREDICT_FOOTBALL_MATCH_SERVICE.findByUserIdAndMatchId(user.getUid(), match.getId());
            if (predict != null) {
                PredictMatchBotEntity predictMatchBot = new PredictMatchBotEntity(match, predict.getTeamOneScore(), predict.getTeamTwoScore());
                this.session.setPredict(predictMatchBot);
            }
        }
        WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);
    }
}
