package cps.handball.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ScraperRunner implements CommandLineRunner {

    private final MatchScraperService matchScraperService;
    private final TeamScraper teamScraper;
    private final PlayerScraper playerScraper;

    public ScraperRunner(MatchScraperService matchScraperService, TeamScraper teamScraper, PlayerScraper playerScraper) {
        this.matchScraperService = matchScraperService;
        this.teamScraper = teamScraper;
        this.playerScraper = playerScraper;
    }

    @Override
    public void run(String... args) throws Exception {
        String overviewUrl = "https://tophaandbold.dk/klubber/herreligaen";
        teamScraper.scrapeTeams(overviewUrl);
        matchScraperService.scrape();
    }
}