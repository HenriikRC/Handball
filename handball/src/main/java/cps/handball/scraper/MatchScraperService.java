package cps.handball.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MatchScraperService {

    public void scrape() {
        final String url = "https://tophaandbold.dk/kampprogram/herreligaen";

        try {
            Document doc = Jsoup.connect(url).get();

            // Assuming each match is in an element with the class 'match-program__row'
            Elements matchRows = doc.select(".match-program__row");

            for (Element row : matchRows) {
                String date = row.select(".match-program__row__date").text();
                Elements teams = row.select(".match-program__row__team");
                String homeTeam = teams.get(0).text();
                String awayTeam = teams.get(1).text();
                String homeScore = row.select(".match-program__row__score__home").text();
                String awayScore = row.select(".match-program__row__score__away").text();

                // Extract the Spilleranalyse link. Adjust the index if necessary.
                String spillerAnalyseLink = row.select(".dropdown-item").attr("href");

                System.out.println("Date: " + date);
                System.out.println("Home Team: " + homeTeam + " - Away Team: " + awayTeam);
                System.out.println("Score: " + homeScore + " vs " + awayScore);
                System.out.println("Spilleranalyse Link: " + spillerAnalyseLink);
                System.out.println("-------------------------------------------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
