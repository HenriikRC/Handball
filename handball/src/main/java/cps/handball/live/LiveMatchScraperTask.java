package cps.handball.live;

import cps.handball.match.Match;
import cps.handball.matchaction.MatchAction;
import cps.handball.matchaction.MatchActionService;
import cps.handball.matchaction.MatchActionType;
import cps.handball.player.PlayerService;
import cps.handball.team.Team;
import jakarta.transaction.Transactional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Component
public class LiveMatchScraperTask {

    private final TaskScheduler taskScheduler;
    private final MatchActionService matchActionService;
    private final PlayerService playerService;
    private ScheduledFuture<?> futureTask;

    public LiveMatchScraperTask(TaskScheduler taskScheduler, MatchActionService matchActionService, PlayerService playerService) {
        this.taskScheduler = taskScheduler;
        this.matchActionService = matchActionService;
        this.playerService = playerService;
    }



    @Transactional
    public void startScraping(Match match) {
        //futureTask = taskScheduler.schedule(() -> scrapeMatch(match), new CronTrigger("*/10 * * * * *"));
    }

    private void scrapeMatch(Match match) {
        System.out.println("Scraping match data from: " + match.getMatchSiteLink());

        WebDriver driver = initSelenium();

        try {
            driver.get(match.getMatchSiteLink());
            Thread.sleep(2000);

            List<WebElement> matchBlocks = driver.findElements(By.cssSelector(".cd-timeline-block-center"));
            List<WebElement> homeBlocks = driver.findElements(By.cssSelector(".cd-timeline-block.left"));
            List<WebElement> awayBlocks = driver.findElements(By.cssSelector(".cd-timeline-block.right"));

            for(WebElement element : matchBlocks) {
                parseMatchBlocks(match, element);
            }
            for(WebElement element : homeBlocks) {
                WebElement timeElement = element.findElement(By.cssSelector(".cd-timeline-img.bounce-in.js-time"));
                String matchTime = timeElement.getText().trim();

                WebElement eventTypeElement = element.findElement(By.tagName("h2"));
                String eventType = eventTypeElement.getText();
                switch(eventType.toLowerCase()) {
                    case("mål"):
                        System.out.println("Parsing GOAL HOME");
                        parseGoal(match, element, matchTime, match.getHomeTeam(),match.getAwayTeam(), MatchActionType.GOAL);
                        break;
                    case("straffemål"):
                        System.out.println("Parsing PENALTY GOAL HOME");
                        parseGoal(match, element, matchTime, match.getHomeTeam(), match.getAwayTeam(), MatchActionType.PENALTY_GOAL);
                        break;
                    case("straffe forbi"):
                        System.out.println("Parsing PENALTY MISSED HOME");

                        break;
                    case("straffe reddet"):

                        break;
                    case("skud forbi"):

                        break;
                    case("skud reddet"):

                        break;
                    case("blokeret"):

                        break;
                    case("fejlaflevering"):

                        break;
                    case("skud på stolpe"):

                        break;
                    case("advarsel"):

                        break;
                    case("rødt kort"):

                        break;
                    case("rødt kort 3 x 2 min."):

                        break;
                    case("blåt kort"):

                        break;
                    case("udvisning"):

                        break;
                    case("passisvt spil"):

                        break;
                    case("team timeout"):

                        break;
                    case("tabt bold"):

                        break;
                }
            }
            for(WebElement element : awayBlocks) {
                WebElement timeElement = element.findElement(By.cssSelector(".cd-timeline-img.bounce-in.js-time"));
                String matchTime = timeElement.getText().trim();

                WebElement eventTypeElement = element.findElement(By.tagName("h2"));
                String eventType = eventTypeElement.getText();
                switch(eventType.toLowerCase()) {
                    case("mål"):
                        System.out.println("Parsing GOAL AWAY");
                        parseGoal(match, element, matchTime, match.getAwayTeam(),match.getHomeTeam(), MatchActionType.GOAL);
                        break;
                    case("straffemål"):;
                    case("straffe forbi"):;
                    case("straffe reddet"):;
                    case("skud forbi"):;
                    case("skud reddet"):;
                    case("blokeret"):;
                    case("fejlaflevering"):;
                    case("skud på stolpe"):;
                    case("advarsel"):;
                    case("rødt kort"):;
                    case("rødt kort 3 x 2 min."):;
                    case("blåt kort"):;
                    case("udvisning"):;
                    case("passisvt spil"):;
                    case("team timeout"):;
                    case("tabt bold"):;
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void parseGoal(Match match, WebElement element, String matchTime, Team attackingTeam, Team defendingTeam, MatchActionType matchActionType) {
        String playerName = getMainPlayerName(element);
        String position = getMainPlayerPosition(element);
        String goalkeeperName = getGoalKeeperName(element);
        String assistingPlayerName = getAssistingPlayerName(element);

        matchActionService.save(match, matchTime, attackingTeam, position,
                playerName, matchActionType, assistingPlayerName, defendingTeam, goalkeeperName);
    }

    private String getMainPlayerName(WebElement element) {
        String playerInfoLine = element.findElements(By.tagName("p")).get(0).getText();
        String[] playerInfoParts = playerInfoLine.split("\\(", 2);
        return playerInfoParts[0].replaceFirst("^\\d+\\.\\s+", "").trim();
    }

    private String getMainPlayerPosition(WebElement element) {
        String playerInfoLine = element.findElements(By.tagName("p")).get(0).getText();
        String[] playerInfoParts = playerInfoLine.split("\\(", 2);
        String positionRaw = playerInfoParts.length > 1 ? playerInfoParts[1] : "";
        return positionRaw.replaceAll("Fra pos\\.\\s+|\\)$", "").trim();
    }

    private String getGoalKeeperName(WebElement element) {
        String goalkeeperInfo = element.findElements(By.tagName("p")).get(1).getText();
        return goalkeeperInfo.replaceFirst("^Målvogter: \\d+\\s+", "").trim();
    }

    private String getAssistingPlayerName(WebElement element) {
        List<WebElement> eventHeaders = element.findElements(By.tagName("h2"));
        for (WebElement header : eventHeaders) {
            if (header.getText().equals("Assist")) {
                WebElement assistInfo = element.findElements(By.tagName("p")).get(eventHeaders.indexOf(header) + 1);
                return assistInfo.getText().trim().replaceFirst("^\\d+\\.\\s+", "");
            }
        }
        return null;
    }

    private void parseMatchBlocks(Match match, WebElement element) {
        MatchAction matchAction = new MatchAction();

        matchAction.setMatch(match);

        WebElement timeElement = element.findElement(By.cssSelector(".cd-timeline-center-img"));
        String matchTime = timeElement.getText().trim();
        matchAction.setMatchTime(matchTime);

        WebElement timeActionElement = element.findElement(By.cssSelector(".cd-timeline-center-content"));
        String timeAction = timeActionElement.getText().trim();
        System.out.println(timeAction);
        switch(timeAction) {
            case("KAMP START"):
                matchAction.setMatchActionType(MatchActionType.START);
                break;
            case("PAUSE"):
                matchAction.setMatchActionType(MatchActionType.PAUSE);
                break;
            case("PAUSE SLUT"):
                matchAction.setMatchActionType(MatchActionType.PAUSE_END);
                break;
            case("FULDTID"):
                matchAction.setMatchActionType(MatchActionType.FULL_TIME);
                break;
            case("KAMP SLUT"):
                matchAction.setMatchActionType(MatchActionType.END);
                break;
        }

        matchActionService.save(matchAction);
    }

    private WebDriver initSelenium() {
        System.setProperty("webdriver.chrome.driver", "handball/src/main/resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors");

        return new ChromeDriver(options);
    }

    public void stopScraping() {
        if (futureTask != null) {
            futureTask.cancel(false);
        }
    }
}