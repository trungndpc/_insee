package vn.insee.admin.retailer.controller.dto;


public class MatchFootballDTO {
    private int id;
    private String teamOne;
    private String teamTwo;
    private String logoTeamOne;
    private String logoTeamTwo;
    private long timeStart;
    private int status;
    private Integer teamOneScore;
    private Integer teamTwoScore;
    private String season;

    private long totalPredict;

    private long totalWin;

    private long totalFailed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(String teamOne) {
        this.teamOne = teamOne;
    }

    public String getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(String teamTwo) {
        this.teamTwo = teamTwo;
    }

    public String getLogoTeamOne() {
        return logoTeamOne;
    }

    public void setLogoTeamOne(String logoTeamOne) {
        this.logoTeamOne = logoTeamOne;
    }

    public String getLogoTeamTwo() {
        return logoTeamTwo;
    }

    public void setLogoTeamTwo(String logoTeamTwo) {
        this.logoTeamTwo = logoTeamTwo;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public long getTotalPredict() {
        return totalPredict;
    }

    public void setTotalPredict(long totalPredict) {
        this.totalPredict = totalPredict;
    }

    public long getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(long totalWin) {
        this.totalWin = totalWin;
    }

    public long getTotalFailed() {
        return totalFailed;
    }

    public void setTotalFailed(long totalFailed) {
        this.totalFailed = totalFailed;
    }
}
