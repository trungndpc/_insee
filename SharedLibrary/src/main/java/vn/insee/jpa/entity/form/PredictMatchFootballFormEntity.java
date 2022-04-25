package vn.insee.jpa.entity.form;

import vn.insee.jpa.entity.FormEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "predict_match_football_form", schema = "public")
public class PredictMatchFootballFormEntity extends FormEntity {
    private int matchId;
    private String season;
    private Integer teamOneScore;
    private Integer teamTwoScore;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getTeamOneScore() {
        return teamOneScore;
    }

    public void setTeamOneScore(Integer teamOneScore) {
        this.teamOneScore = teamOneScore;
    }

    public Integer getTeamTwoScore() {
        return teamTwoScore;
    }

    public void setTeamTwoScore(Integer teamTwoScore) {
        this.teamTwoScore = teamTwoScore;
    }
}
