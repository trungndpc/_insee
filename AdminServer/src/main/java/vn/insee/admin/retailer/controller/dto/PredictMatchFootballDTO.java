package vn.insee.admin.retailer.controller.dto;

public class PredictMatchFootballDTO {
    private UserDTO user;

    private MatchFootballDTO match;
    private int matchId;
    private String season;
    private Integer teamOneScore;
    private Integer teamTwoScore;
    private Integer teamWin;
    private int status;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

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

    public MatchFootballDTO getMatch() {
        return match;
    }

    public void setMatch(MatchFootballDTO match) {
        this.match = match;
    }
}
