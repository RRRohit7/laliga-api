package com.learn.laliga.model;

public class SeasonDetails {
    private String club;
    private Long tablePosition;
    private Long wins;
    private Long loses;
    private Long ties;
    private Long points;
    private String season;


    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public Long getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(Long tablePosition) {
        this.tablePosition = tablePosition;
    }

    public Long getWins() {
        return wins;
    }

    public void setWins(Long wins) {
        this.wins = wins;
    }

    public Long getLoses() {
        return loses;
    }

    public void setLoses(Long loses) {
        this.loses = loses;
    }

    public Long getTies() {
        return ties;
    }

    public void setTies(Long ties) {
        this.ties = ties;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
