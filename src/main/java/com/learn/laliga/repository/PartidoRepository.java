package com.learn.laliga.repository;

import com.learn.laliga.model.H2HClashResult;
import com.learn.laliga.model.IGoalsInSeason;
import com.learn.laliga.data.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    @Query("SELECT p.homeTeam AS club, p.seasonYear AS season, sum(p.homeTeamGoals) AS goalsScored, sum(p.awayTeamGoals) AS goalsConceded FROM Partido AS p WHERE p.homeTeam= :home_team AND  p.seasonYear = :season_year")
    IGoalsInSeason getHomeTeamGoalsInaSeason(@Param("season_year") Long season_year, @Param("home_team") String home_team);

    @Query("SELECT  p.awayTeam AS club, p.seasonYear AS season, sum(p.awayTeamGoals) AS goalsScored, sum(p.homeTeamGoals) AS goalsConceded FROM Partido AS p WHERE p.awayTeam= :away_team AND p.seasonYear = :season_year")
    IGoalsInSeason getAwayTeamGoalsInaSeason(@Param("season_year") Long season_year, @Param("away_team") String away_team);

    List<Partido> findBySeasonYear(Long seasonYear);

    @Query("SELECT p FROM Partido AS p WHERE p.homeTeam = :home_team AND p.awayTeam = :away_team AND p.seasonYear = :season_year")
    Partido getHead2HeadClashResult(@Param("home_team") String home_team, @Param("away_team") String away_team, @Param("season_year") Long season_year);
}
