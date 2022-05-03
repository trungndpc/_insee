package vn.insee.retailer.bot;

import org.json.JSONObject;
import vn.insee.retailer.bot.entity.football.MatchBotEntity;
import vn.insee.retailer.bot.entity.football.PredictMatchBotEntity;

import java.util.HashMap;
import java.util.Map;

public class PredictFootballSession {
    private long timeStart;
    private int promotionId;
    private MatchBotEntity match;

    private Integer predictId;
    private PredictMatchBotEntity predict;
    private Map<String, PredictFootballSS> questions;
    private String waitingQuestionId;

    private String season;

    public PredictFootballSession() {
    }

    public void putQuestion(String id, PredictFootballSS question) {
        if (questions == null) {
            questions = new HashMap<>();
        }
        questions.put(id, question);
    }

    public String getId(Class zclass) {
        for (String id: questions.keySet()) {
            PredictFootballSS predictFootballSS = questions.get(id);
            if (predictFootballSS.getZclass() == zclass) {
                return id;
            }
        }
        return null;
    }

    public PredictFootballSS getQuestion(Class zclass) {
        for (String id: questions.keySet()) {
            PredictFootballSS predictFootballSS = questions.get(id);
            if (predictFootballSS.getZclass() == zclass) {
                return predictFootballSS;
            }
        }
        return null;
    }

    public static final class PredictFootballSS {
        private final String id;
        private final Class zclass;
        private final JSONObject json;

        public PredictFootballSS(String id, Class zclass, JSONObject json) {
            this.id = id;
            this.zclass = zclass;
            this.json = json;
        }

        public String getId() {
            return id;
        }

        public Class getZclass() {
            return zclass;
        }

        public JSONObject getJson() {
            return json;
        }
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public MatchBotEntity getMatch() {
        return match;
    }

    public void setMatch(MatchBotEntity match) {
        this.match = match;
    }

    public PredictMatchBotEntity getPredict() {
        return predict;
    }

    public void setPredict(PredictMatchBotEntity predict) {
        this.predict = predict;
    }

    public Map<String, PredictFootballSS> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, PredictFootballSS> questions) {
        this.questions = questions;
    }

    public String getWaitingQuestionId() {
        return waitingQuestionId;
    }

    public void setWaitingQuestionId(String waitingQuestionId) {
        this.waitingQuestionId = waitingQuestionId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getPredictId() {
        return predictId;
    }

    public void setPredictId(Integer predictId) {
        this.predictId = predictId;
    }
}
