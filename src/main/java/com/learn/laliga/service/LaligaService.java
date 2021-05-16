package com.learn.laliga.service;

import com.learn.laliga.model.GoalsInASeason;
import com.learn.laliga.model.H2HClashResult;
import com.learn.laliga.model.IGoalsInSeason;
import com.learn.laliga.data.Partido;
import com.learn.laliga.model.SeasonDetails;
import com.learn.laliga.repository.PartidoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LaligaService {

@Autowired
PartidoRepository partidoRepository;

    public GoalsInASeason getTeamGoalsInASeason(Long season, String club){
        IGoalsInSeason home =  partidoRepository.getHomeTeamGoalsInaSeason(season, club);
        IGoalsInSeason away = partidoRepository.getAwayTeamGoalsInaSeason(season,club);
        GoalsInASeason totalGoals = new GoalsInASeason();
        totalGoals.setClub(home.getClub());
        totalGoals.setSeason(String.format("%s-%d", home.getSeason(), Long.parseLong(home.getSeason()) + 1L));
        totalGoals.setGoalsScored(home.getGoalsScored() + away.getGoalsScored());
        totalGoals.setGoalsConceded(home.getGoalsConceded() + away.getGoalsConceded());
        return totalGoals;
    }

    public H2HClashResult getHead2HeadClashResultInASeason(String homeTeam, String awayTeam, Long season){
        H2HClashResult result = new H2HClashResult();
        Partido homeResult = partidoRepository.getHead2HeadClashResult(homeTeam, awayTeam, season);
        Partido awayResult = partidoRepository.getHead2HeadClashResult(awayTeam, homeTeam, season);
        result.setHomeResult(homeResult.getResult().equals("H") ? "Won" : homeResult.getResult().equals("A") ? "Lost" : "Draw");
        result.setAwayResult(awayResult.getResult().equals("A") ? "Won" : awayResult.getResult().equals("H") ? "Lost" : "Draw");
        return result;
    }

    public LinkedHashMap<String, SeasonDetails> getSeasonDetails(Long season){
        List<Partido> allMatches = partidoRepository.findBySeasonYear(season);
        Comparator<SeasonDetails> compareTeams = new Comparator<SeasonDetails>() {
            @Override
            public int compare(SeasonDetails team1, SeasonDetails team2) {
                return compareTwoRowsInPointsTable(team1, team2, season);
            }
        };
        Map<String, SeasonDetails> tableData = new HashMap<>();
        allMatches.forEach(row ->{
            populateSeasonDetailsFromMatchData(tableData, row);
        });
        LinkedHashMap<String, SeasonDetails> sortedMap = tableData.entrySet().stream()
                .sorted(Map.Entry.<String, SeasonDetails>comparingByValue(compareTeams))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return  sortedMap;
    }

    private int compareTwoRowsInPointsTable(SeasonDetails team1, SeasonDetails team2, Long season){
        Long team1points = team1.getPoints();
        Long team2points = team2.getPoints();
        if(team1points == team2points){
            Partido homeResult = partidoRepository.getHead2HeadClashResult(team1.getClub(), team2.getClub(), season);
            Partido awayResult = partidoRepository.getHead2HeadClashResult(team2.getClub(), team1.getClub(), season);
            int upperHand = 0;
            upperHand = homeResult.getResult().equals("H") ? upperHand + 1 : homeResult.getResult().equals("A") ? upperHand - 1 : upperHand;
            upperHand = awayResult.getResult().equals("A") ? upperHand + 1 : awayResult.getResult().equals("H") ? upperHand - 1 : upperHand;
            if(upperHand == 0){
                Long H2HGoalDiff = homeResult.getHomeTeamGoals() - homeResult.getAwayTeamGoals() + awayResult.getAwayTeamGoals() - awayResult.getHomeTeamGoals();
                if(H2HGoalDiff == 0){
                    GoalsInASeason team1GD = getTeamGoalsInASeason(season, team1.getClub());
                    GoalsInASeason team2GD = getTeamGoalsInASeason(season, team2.getClub());
                    Long goalDiff = team1GD.getGoalsScored() - team1GD.getGoalsConceded() - team2GD.getGoalsScored() + team2GD.getGoalsConceded();
                    int upperHandinGD = 0;
                    upperHandinGD = goalDiff > 0 ? -1 : goalDiff < 0 ? 1 : 0;
                    return upperHandinGD;
                }
                return H2HGoalDiff > 0 ? -1 : H2HGoalDiff < 0 ? 1 : 0;
            }
            return -upperHand;
        }
        Long pointDiff = team1points - team2points;
        return pointDiff > 0 ? -1 : pointDiff < 0 ? 1 : 0;
    }

    private void populateSeasonDetailsFromMatchData(Map<String, SeasonDetails> tableData, Partido row) {
        if(tableData.get(row.getHomeTeam()) == null){
            SeasonDetails returnedDetails = new SeasonDetails();
            returnedDetails.setClub(row.getHomeTeam());
            if(row.getResult().equals("H")){
                returnedDetails.setWins(1L);
                returnedDetails.setLoses(0L);
                returnedDetails.setTies(0L);
            }
            else if(row.getResult().equals("D")){
                returnedDetails.setWins(0L);
                returnedDetails.setLoses(0L);
                returnedDetails.setTies(1L);
            }
            else{
                returnedDetails.setWins(0L);
                returnedDetails.setLoses(1L);
                returnedDetails.setTies(0L);
            }
            returnedDetails.setPoints(returnedDetails.getWins() * 3 + returnedDetails.getTies());
            returnedDetails.setSeason(String.format("%d-%d", row.getSeasonYear(), row.getSeasonYear() + 1L));
            tableData.put(row.getHomeTeam(), returnedDetails);
        }
        else{
            SeasonDetails returnedDetails = tableData.get(row.getHomeTeam());
            if(row.getResult().equals("H")){
                returnedDetails.setWins(returnedDetails.getWins() + 1L);
            }
            else if(row.getResult().equals("D")){
                returnedDetails.setTies(returnedDetails.getTies() + 1L);
            }
            else{
                returnedDetails.setLoses(returnedDetails.getLoses() + 1L);
            }
            returnedDetails.setPoints(returnedDetails.getWins() * 3 + returnedDetails.getTies());
            returnedDetails.setSeason(String.format("%d-%d", row.getSeasonYear(), row.getSeasonYear() + 1L));
            tableData.put(row.getHomeTeam(), returnedDetails);
        }
        if(tableData.get(row.getAwayTeam()) == null){
            SeasonDetails returnedDetails = new SeasonDetails();
            returnedDetails.setClub(row.getAwayTeam());
            if(row.getResult().equals("H")){
                returnedDetails.setWins(0L);
                returnedDetails.setLoses(1L);
                returnedDetails.setTies(0L);
            }
            else if(row.getResult().equals("D")){
                returnedDetails.setWins(0L);
                returnedDetails.setLoses(0L);
                returnedDetails.setTies(1L);
            }
            else{
                returnedDetails.setWins(1L);
                returnedDetails.setLoses(0L);
                returnedDetails.setTies(0L);
            }
            returnedDetails.setPoints(returnedDetails.getWins() * 3 + returnedDetails.getTies());
            returnedDetails.setSeason(String.format("%d-%d", row.getSeasonYear(), row.getSeasonYear() + 1L));
            tableData.put(row.getAwayTeam(), returnedDetails);
        }
        else{
            SeasonDetails returnedDetails = tableData.get(row.getAwayTeam());
            if(row.getResult().equals("H")){
                returnedDetails.setLoses(returnedDetails.getLoses() + 1L);
            }
            else if(row.getResult().equals("D")){
                returnedDetails.setTies(returnedDetails.getTies() + 1L);
            }
            else{
                returnedDetails.setWins(returnedDetails.getWins() + 1L);
            }
            returnedDetails.setPoints(returnedDetails.getWins() * 3 + returnedDetails.getTies());
            returnedDetails.setSeason(String.format("%d-%d", row.getSeasonYear(), row.getSeasonYear() + 1L));
            tableData.put(row.getAwayTeam(), returnedDetails);
        }
    }
}
