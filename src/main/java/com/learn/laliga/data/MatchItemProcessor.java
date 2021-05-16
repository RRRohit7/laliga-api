package com.learn.laliga.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MatchItemProcessor implements ItemProcessor<MatchInput, Partido> {

    private static final Logger log = LoggerFactory.getLogger(MatchItemProcessor.class);

    @Override
    public Partido process(MatchInput matchInput) throws Exception {
        Partido partido = new Partido();
        partido.setSeasonYear(Long.parseLong(matchInput.getSeasonNo().substring(0,4)));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        partido.setMatchDate(LocalDate.parse(matchInput.getMatchDate(), formatter));
        partido.setHomeTeam(matchInput.getHomeTeam());
        partido.setAwayTeam(matchInput.getAwayTeam());
        partido.setHomeTeamGoals(Long.parseLong(matchInput.getFullTimeHomeGoals()));
        partido.setAwayTeamGoals(Long.parseLong(matchInput.getFullTimeAwayGoals()));
        partido.setResult(matchInput.getFullTimeResult());
        log.info(String.format("Converting (%s) into (%s)", matchInput, partido));
        return partido;
    }
}
