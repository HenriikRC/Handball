package cps.handball.scraper;

import cps.handball.team.Team;
import cps.handball.team.TeamService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TeamScraper {

    private final TeamService teamService;
    private final PlayerScraper playerScraper;

    public TeamScraper(PlayerScraper playerScraper, TeamService teamService){
        this.playerScraper = playerScraper;
        this.teamService = teamService;
    }

    public void scrapeTeams(String overviewUrl) {
        try {
            Document overviewDoc = Jsoup.connect(overviewUrl).get();
            Elements teamLinks = overviewDoc.select(".league-team__team a");

            for (Element teamLink : teamLinks) {
                String teamPageUrl = teamLink.attr("abs:href");
                scrapeTeam(teamPageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scrapeTeam(String teamPageUrl) {
        try {
            System.out.println("Accessing URL: " + teamPageUrl);
            Document teamPageDoc = Jsoup.connect(teamPageUrl).get();

            Team team = new Team();
            team.setName(teamPageDoc.title());
            teamService.save(team);
            String teamPageLink = teamPageDoc.select("a.ls-team__info__ul__li__link[href*='/tophaandbold_theme/teams/view']").attr("abs:href");
            if (!teamPageLink.isEmpty()) {
                playerScraper.scrapePlayers(teamPageLink, team);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}