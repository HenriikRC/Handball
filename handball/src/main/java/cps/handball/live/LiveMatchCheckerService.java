package cps.handball.live;

import cps.handball.match.Match;
import cps.handball.match.MatchService;
import cps.handball.live.LiveMatchScraperTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Scheduled(fixedRate = 300000)
    public void checkForLiveMatches() {
        List<Match> liveMatches = new ArrayList<>();
        liveMatches.add(matchService.findMatchById(168L));
        liveMatches.forEach(match -> {
            if (!activeScrapers.containsKey(match.getId())) {
                LiveMatchScraperTask newTask = context.getBean(LiveMatchScraperTask.class);
                newTask.startScraping(match);
                activeScrapers.put(match.getId(), newTask);
                System.out.println("Match is live: " + match.getId());
            }
        });

        stopFinishedMatches();
    }

    private void stopFinishedMatches() {
        activeScrapers.entrySet().removeIf(entry -> {
            Match match = matchService.findMatchById(entry.getKey());
            if (match.isFinished()) {
                entry.getValue().stopScraping();
                return true;
            }
            return false;
        });
    }
}