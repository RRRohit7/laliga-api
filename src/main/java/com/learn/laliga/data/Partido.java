package com.learn.laliga.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Partido {
    // Partido is spanish for match
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partidoId;
    private Long seasonYear;
    private LocalDate matchDate;
    private String homeTeam;
    private String awayTeam;
    private Long homeTeamGoals;
    private Long awayTeamGoals;
    private String result;

    public Long getPartidoId() {
        return partidoId;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "seasonYear=" + seasonYear +
                ", matchDate=" + matchDate +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", homeTeamGoals=" + homeTeamGoals +
                ", awayTeamGoals=" + awayTeamGoals +
                ", result='" + result + '\'' +
                '}';
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }

    public Long getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(Long seasonYear) {
        this.seasonYear = seasonYear;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
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

    public Long getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(Long homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public Long getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(Long awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
