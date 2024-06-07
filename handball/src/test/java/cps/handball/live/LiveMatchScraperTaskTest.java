package cps.handball.live;

import cps.handball.match.Match;
import cps.handball.matchaction.MatchActionService;
import cps.handball.match.MatchService;
import cps.handball.matchaction.MatchActionMapper;
import cps.handball.match.MatchMapper;
import cps.handball.team.Team;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.scheduling.TaskScheduler;

import java.sql.Timestamp;

public class LiveMatchScraperTaskTest {

    @Test
    public void scraperPerformanceTest() {
        TaskScheduler taskScheduler = Mockito.mock(TaskScheduler.class);
        MatchActionService matchActionService = Mockito.mock(MatchActionService.class);
        MatchService matchService = Mockito.mock(MatchService.class);
        MatchActionEventPublisher eventPublisher = Mockito.mock(MatchActionEventPublisher.class);
        MatchActionMapper matchActionMapper = Mockito.mock(MatchActionMapper.class);
        MatchMapper matchMapper = Mockito.mock(MatchMapper.class);
        LiveMatchScraperTask liveMatchScraperTask = new LiveMatchScraperTask(taskScheduler,
                                                                             matchActionService,
                                                                             matchService,
                                                                             eventPublisher,
                                                                             matchActionMapper);
        Match match = new Match();
        match.setMatchSiteLink("https://tophaandbold.dk/match/590026");
        match.setId(1L);
        match.setMatchTime(Timestamp.valueOf("2023-01-01 00:00:00"));
        match.setHomeTeam(new Team("Home Team"));
        match.setAwayTeam(new Team("Away Team"));
        match.setHomeTeamGoals(10);
        match.setAwayTeamGoals(8);
        match.setPlayerAnalysisLink("link");
        match.setFinished(false);
        long totalDuration = 0;
        int iterations = 25;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            liveMatchScraperTask.scrapeMatch(match);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            totalDuration += duration;
        }
        double averageDuration = totalDuration / (double) iterations;
        System.out.println("Average time taken to execute scrapeMatch: " + averageDuration / 1_000_000 + " milliseconds");
    }
}
