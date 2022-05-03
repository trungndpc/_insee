package vn.insee.retailer.bot.entity.football;

public class PredictMatchBotEntity {
    private MatchBotEntity match;
    private int goalTeamA;
    private int goalTeamB;

    private int teamWin;

    private Integer id;


    public PredictMatchBotEntity() {
    }

    public PredictMatchBotEntity(MatchBotEntity match, int teamWin) {
        this.match = match;
        this.teamWin  = teamWin;
    }

    public MatchBotEntity getMatch() {
        return match;
    }

    public void setMatch(MatchBotEntity match) {
        this.match = match;
    }

    public int getGoalTeamA() {
        return goalTeamA;
    }

    public void setGoalTeamA(int goalTeamA) {
        this.goalTeamA = goalTeamA;
    }

    public int getGoalTeamB() {
        return goalTeamB;
    }

    public void setGoalTeamB(int goalTeamB) {
        this.goalTeamB = goalTeamB;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTeamWin() {
        return teamWin;
    }

    public void setTeamWin(int teamWin) {
        this.teamWin = teamWin;
    }
}
