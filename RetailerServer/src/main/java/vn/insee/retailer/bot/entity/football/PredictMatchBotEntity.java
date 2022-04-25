package vn.insee.retailer.bot.entity.football;

public class PredictMatchBotEntity {
    private MatchBotEntity match;
    private int goalTeamA;
    private int goalTeamB;

    public PredictMatchBotEntity(MatchBotEntity match, int goalTeamA, int goalTeamB) {
        this.match = match;
        this.goalTeamA = goalTeamA;
        this.goalTeamB = goalTeamB;
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
}
