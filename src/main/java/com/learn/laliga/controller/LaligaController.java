package com.learn.laliga.controller;

import com.learn.laliga.data.Partido;
import com.learn.laliga.model.GoalsInASeason;
import com.learn.laliga.model.H2HClashResult;
import com.learn.laliga.model.SeasonDetails;
import com.learn.laliga.service.LaligaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class LaligaController {

    @Autowired
    LaligaService laligaService;

    @GetMapping("/goalsinaseason")
    public GoalsInASeason teamGoalsInASeason(@RequestParam Long season, @RequestParam String club){
        return laligaService.getTeamGoalsInASeason(season,club);
    }
    @GetMapping("/Head2Head")
    public H2HClashResult head2headClashResult(@RequestParam Long season, @RequestParam String homeTeam, @RequestParam String awayTeam){
        H2HClashResult clashResult = laligaService.getHead2HeadClashResultInASeason(homeTeam, awayTeam, season);
        return clashResult;
    }

    @GetMapping("/PointsTable/{season}")
    public LinkedHashMap<String, SeasonDetails> pointsTableofTheSeason(@PathVariable Long season){
        return laligaService.getSeasonDetails(season);
    }
}
