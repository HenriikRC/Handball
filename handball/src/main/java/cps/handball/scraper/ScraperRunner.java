package cps.handball.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ScraperRunner implements CommandLineRunner {

    private final MatchScraperService matchScraperService;

    @Autowired
    public ScraperRunner(MatchScraperService matchScraperService){
        this.matchScraperService = matchScraperService;
    }
    @Override
    public void run(String... args) throws Exception {
        matchScraperService.scrape();
    }
}
