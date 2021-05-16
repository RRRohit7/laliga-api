package com.learn.laliga.data;

public class MatchInput {
    private String seasonNo;
    private String matchDate;
    private String homeTeam;
    private String awayTeam;
    private String fullTimeHomeGoals;
    private String fullTimeAwayGoals;
    private String fullTimeResult;
    private String halfTimeHomeGoals;
    private String halfTimeAwayGoals;
    private String halfTimeResult;

    @Override
    public String toString() {
        return "MatchInput{" +
                "seasonNo='" + seasonNo + '\'' +
                ", matchDate='" + matchDate + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", fullTimeHomeGoals='" + fullTimeHomeGoals + '\'' +
                ", fullTimeAwayGoals='" + fullTimeAwayGoals + '\'' +
                ", fullTimeResult='" + fullTimeResult + '\'' +
                ", halfTimeHomeGoals='" + halfTimeHomeGoals + '\'' +
                ", halfTimeAwayGoals='" + halfTimeAwayGoals + '\'' +
                ", halfTimeResult='" + halfTimeResult + '\'' +
                '}';
    }

    public String getSeasonNo() {
        return seasonNo;
    }

    public void setSeasonNo(String seasonNo) {
        this.seasonNo = seasonNo;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getFullTimeHomeGoals() {
        return fullTimeHomeGoals;
    }

    public void setFullTimeHomeGoals(String fullTimeHomeGoals) {
        this.fullTimeHomeGoals = fullTimeHomeGoals;
    }

    public String getFullTimeAwayGoals() {
        return fullTimeAwayGoals;
    }

    public void setFullTimeAwayGoals(String fullTimeAwayGoals) {
        this.fullTimeAwayGoals = fullTimeAwayGoals;
    }

    public String getFullTimeResult() {
        return fullTimeResult;
    }

    public void setFullTimeResult(String fullTimeResult) {
        this.fullTimeResult = fullTimeResult;
    }

    public String getHalfTimeHomeGoals() {
        return halfTimeHomeGoals;
    }

    public void setHalfTimeHomeGoals(String halfTimeHomeGoals) {
        this.halfTimeHomeGoals = halfTimeHomeGoals;
    }

    public String getHalfTimeAwayGoals() {
        return halfTimeAwayGoals;
    }

    public void setHalfTimeAwayGoals(String halfTimeAwayGoals) {
        this.halfTimeAwayGoals = halfTimeAwayGoals;
    }

    public String getHalfTimeResult() {
        return halfTimeResult;
    }

    public void setHalfTimeResult(String halfTimeResult) {
        this.halfTimeResult = halfTimeResult;
    }
}
