package vn.insee.jpa.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "match", schema="promotion")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private int teamOne;
    private int teamTwo;
    private ZonedDateTime timeStart;
    private ZonedDateTime timeEnd;
    private int status;
    private Integer teamOneScore;
    private Integer teamTwoScore;
    private Integer seasonId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(int teamOne) {
        this.teamOne = teamOne;
    }

    public int getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(int teamTwo) {
        this.teamTwo = teamTwo;
    }

    public ZonedDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(ZonedDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public ZonedDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(ZonedDateTime timeEnd) {
        this.timeEnd = timeEnd;
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

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }
}
