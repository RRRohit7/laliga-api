package com.learn.laliga.data;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final String[] NAMES = new String[]{
            "season_no", "match_date", "home_team", "away_team", "full_time_home_goals",
            "full_time_away_goals", "full_time_result","half_time_home_goals",
            "half_time_away_goals", "half_time_result"
    };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<MatchInput> reader(){
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("matchInputReader")
                .resource(new ClassPathResource("Laliga_Matches_1995-2020.csv"))
                .delimited()
                .names(NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>(){{
                    setTargetType(MatchInput.class);
                }}).build();
    }

    @Bean
    public MatchItemProcessor processor(){
        return new MatchItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Partido> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Partido>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO partido (season_year, match_date, home_team, away_team, home_team_goals, away_team_goals, result) VALUES (:seasonYear, :matchDate, :homeTeam, :awayTeam, :homeTeamGoals, :awayTeamGoals, :result)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Partido> writer) {
        return stepBuilderFactory.get("step1")
                .<MatchInput, Partido> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

}
