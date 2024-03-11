package cps.handball.scraper;

import cps.handball.match.Match;
import cps.handball.match.MatchRepository;
import cps.handball.match.MatchService;
import cps.handball.team.Team;
import cps.handball.team.TeamService;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class MatchScraperService {

    private final MatchService matchService;
    private final TeamService teamService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
    @Autowired
    public MatchScraperService(MatchService matchService, TeamService teamService){
        this.matchService = matchService;
        this.teamService = teamService;
    }
    public void scrape() {
        final String url = "https://tophaandbold.dk/kampprogram/herreligaen";

        try {

            Document doc = Jsoup.connect(url).get();


            Elements matchRows = doc.select(".match-program__row");

            for (Element row : matchRows) {
                Match match = new Match();

                String date = row.select(".match-program__row__date").text();
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
                Timestamp timestamp = Timestamp.from(instant);
                match.setMatchTime(timestamp);

                Elements teams = row.select(".match-program__row__team");
                String homeTeamName = teams.eq(0).text();
                Team homeTeam = new Team();
                homeTeam.setName(homeTeamName);
                match.setHomeTeam(homeTeam);

                String awayTeamName = teams.eq(1).text();
                Team awayTeam = new Team();
                awayTeam.setName(awayTeamName);
                match.setAwayTeam(awayTeam);


                String homeScore = row.select(".match-program__row__score__home").text();
                if(homeScore.isBlank()) {
                    match.setHomeTeamGoals(0);
                } else {
                    match.setHomeTeamGoals(Integer.parseInt(homeScore));
                }

                String awayScore = row.select(".match-program__row__score__away").text();
                if(awayScore.isBlank()) {
                    match.setAwayTeamGoals(0);
                } else {
                    match.setAwayTeamGoals(Integer.parseInt(awayScore));
                    match.setFinished(true);
                }

                String playerAnalysisLink = row.select(".dropdown-item").eq(1).attr("abs:href"); // Using .eq(1) to select the second dropdown-item
                match.setPlayerAnalysisLink(playerAnalysisLink);

                String matchSiteLink = row.select(".match-program__row__livescore a").attr("abs:href");
                if(matchSiteLink.contains("https://tophaandbold.dk")){
                    match.setMatchSiteLink(matchSiteLink);
                }

                saveMatchWithTeams(match);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveMatchWithTeams(Match match) {
        Team homeTeam = teamService.findOrCreateByName(match.getHomeTeam().getName());
        Team awayTeam = teamService.findOrCreateByName(match.getAwayTeam().getName());

        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);

        matchService.saveMatch(match);
    }
}
