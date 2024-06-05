package cps.handball.live;

import cps.handball.match.Match;
import cps.handball.match.MatchDTO;
import cps.handball.match.MatchService;
import cps.handball.live.LiveMatchScraperTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@EnableScheduling
public class LiveMatchCheckerService {

    private final MatchService matchService;
    private final ApplicationContext context;
    private Map<Long, LiveMatchScraperTask> activeScrapers = new HashMap<>();

    @Autowired
    public LiveMatchCheckerService(MatchService matchService, ApplicationContext context) {
        this.matchService = matchService;
        this.context = context;
    }

    @Scheduled(fixedRate = 30000)
    public void checkForLiveMatches() {
        List<Match> unfinishedMatches = matchService.findUnfinishedMatches();
        LocalDateTime currentTime = LocalDateTime.now();
        unfinishedMatches.forEach(match -> {
            Timestamp matchTimestamp = match.getMatchTime();
            LocalDateTime matchTime = LocalDateTime.
                    ofInstant(Instant.ofEpochMilli(matchTimestamp.getTime()), ZoneId.systemDefault());
            if (currentTime.plusMinutes(5).plusSeconds(30).isAfter(matchTime)) {
                if (!activeScrapers.containsKey(match.getId())) {
                    LiveMatchScraperTask newTask = context.getBean(LiveMatchScraperTask.class);
                    newTask.startScraping(match);
                    activeScrapers.put(match.getId(), newTask);
                }
            }
        });
    }
}