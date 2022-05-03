package vn.insee.jpa.entity;

import vn.insee.jpa.entity.base.BaseEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "match_football", schema = "public")
public class MatchFootballEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String teamOne;
    private String teamTwo;
    private String logoTeamOne;
    private String logoTeamTwo;
    private ZonedDateTime timeStart;
    private ZonedDateTime timeEnd;
    private int status;
    private Integer teamOneScore;
    private Integer teamTwoScore;
    private String season;

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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
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
}
