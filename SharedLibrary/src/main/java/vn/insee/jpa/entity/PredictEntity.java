package vn.insee.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "predict", schema="promotion")
public class PredictEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int customerId;
    private int matchId;
    private Integer seasonId;
    private Integer teamOneScore;
    private Integer teamTwoScore;
    private Integer teamWin;
    private int status;
    private Integer scoreReceived;
    private Integer promotionId;
    private Integer giftId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
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

    public Integer getTeamWin() {
        return teamWin;
    }

    public void setTeamWin(Integer teamWin) {
        this.teamWin = teamWin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getScoreReceived() {
        return scoreReceived;
    }

    public void setScoreReceived(Integer scoreReceived) {
        this.scoreReceived = scoreReceived;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }
}
