package cps.handball.live;

import cps.handball.match.Match;
import cps.handball.matchaction.MatchAction;
import cps.handball.matchaction.MatchActionService;
import cps.handball.matchaction.MatchActionType;
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
    private final MatchActionEventPublisher eventPublisher;
    private ScheduledFuture<?> futureTask;

    public LiveMatchScraperTask(TaskScheduler taskScheduler,
                                MatchActionService matchActionService,
                                MatchActionEventPublisher eventPublisher) {
        this.taskScheduler = taskScheduler;
        this.matchActionService = matchActionService;
        this.eventPublisher = eventPublisher;
    }



    @Transactional
    public void startScraping(Match match) {
        futureTask = taskScheduler.schedule(() -> scrapeMatch(match), new CronTrigger("*/10 * * * * *"));
    }

    private void scrapeMatch(Match match) {

        System.out.println("Scraping match data from: " + match.getMatchSiteLink());
        WebDriver driver = initSelenium();
        try {
            String matchSiteLink = match.getMatchSiteLink();
            if (matchSiteLink == null || matchSiteLink.isEmpty()) {
                System.out.println("Match site link is null or empty for match: " + match.toString());
                return;
            }
            driver.get(matchSiteLink);
            Thread.sleep(2000);

            List<WebElement> matchBlocks = driver.findElements(By.cssSelector(".cd-timeline-block-center"));
            for (WebElement element : matchBlocks) {
                WebElement timeElement = element.findElement(By.cssSelector(".cd-timeline-center-img"));
                String matchTime = timeElement.getText().trim();
                System.out.println("Found " + matchBlocks.size() + " match blocks.");
                matchBlocks.forEach(block -> System.out.println(block.getText()));
                MatchActionType matchActionType = determineMatchActionTypeFromBlock(element);
                if (matchActionType != null && !matchActionService.checkIfExists(match.getId(), matchTime, matchActionType)) {
                    parseMatchBlocks(match, element);
                }
            }
            System.out.println("Parsed all TIME ACTIONS");

            processMatchActions(driver, match, true); // for home team
            processMatchActions(driver, match, false); // for away team

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void processMatchActions(WebDriver driver, Match match, boolean isHomeTeam) {
        List<WebElement> blocks = driver.findElements(By.cssSelector(isHomeTeam ? ".cd-timeline-block.left" : ".cd-timeline-block.right"));
        for (WebElement element : blocks) {
            WebElement timeElement = element.findElement(By.cssSelector(".cd-timeline-img.bounce-in.js-time"));
            String matchTime = timeElement.getText().trim();
            WebElement eventTypeElement = element.findElement(By.tagName("h2"));
            String eventType = eventTypeElement.getText().trim();
            MatchActionType matchActionType = determineMatchActionType(eventType);

            if (matchActionType != null && !matchActionService.checkIfExists(match.getId(), matchTime, matchActionType)) {
                parseShot(match, element, matchTime,
                        isHomeTeam ? match.getHomeTeam() : match.getAwayTeam(),
                        isHomeTeam ? match.getAwayTeam() : match.getHomeTeam(),
                        matchActionType);
            }
        }
        System.out.println("Parsed all " + (isHomeTeam ? "HOME" : "AWAY") + " ACTIONS");
    }



    private void parseShot(Match match, WebElement element, String matchTime, Team attackingTeam, Team defendingTeam, MatchActionType matchActionType) {
        String playerName = getMainPlayerName(element);
        String position = getMainPlayerPosition(element);
        String goalkeeperName = getGoalKeeperName(element);
        String assistingPlayerName = getAssistingPlayerName(element);

        matchActionService.save(match, matchTime, attackingTeam, position,
                playerName, matchActionType, assistingPlayerName, defendingTeam, goalkeeperName);
    }


    private MatchActionType determineMatchActionType(String eventType) {
        switch(eventType.toLowerCase()) {
            case("mål"):
                return MatchActionType.GOAL;
            case("straffemål"):
                return MatchActionType.PENALTY_GOAL;
            case("straffe forbi"):;
                return MatchActionType.PENALTY_MISS;
            case("straffe reddet"):
                return MatchActionType.PENALTY_SAVE;
            case("skud forbi"):
                return MatchActionType.MISSED;
            case("skud reddet"):
                return MatchActionType.SAVED;
            case("blokeret"):
                return null;
            case("fejlaflevering"):
                return null;
            case("skud på stolpe"):
                return null;
            case("advarsel"):
                return null;
            case("rødt kort"):
                return null;
            case("rødt kort 3 x 2 min."):
                return null;
            case("blåt kort"):
                return null;
            case("udvisning"):
                return null;
            case("passisvt spil"):
                return null;
            case("team timeout"):
                return null;
            case("tabt bold"):
                return null;
            default:
                return null;
        }
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

    private String getAssistingPlayerName(WebElement element) {
        List<WebElement> allContent = element.findElements(By.xpath(".//*"));
        boolean assistFound = false;

        for (WebElement content : allContent) {
            if (content.getTagName().equalsIgnoreCase("h2") && content.getText().trim().equalsIgnoreCase("Assist")) {
                assistFound = true;
                continue;
            }

            if (assistFound && content.getTagName().equalsIgnoreCase("p")) {
                String text = content.getText().trim();
                return text.replaceFirst("^\\d+\\.\\s+", "");
            }
        }

        return null;
    }

    private String getGoalKeeperName(WebElement element) {
        String goalkeeperInfo = element.findElements(By.tagName("p")).get(1).getText();
        return goalkeeperInfo.replaceFirst("^Målvogter:\\s*\\d+\\.\\s+", "");
    }

    private MatchActionType determineMatchActionTypeFromBlock(WebElement element) {
        String text = element.getText().toUpperCase();
        switch (text) {
            case "KAMP START": return MatchActionType.START;
            case "PAUSE": return MatchActionType.PAUSE;
            case("PAUSE SLUT"):return MatchActionType.PAUSE_END;
            case("FULDTID"):return MatchActionType.FULL_TIME;
            case("KAMP SLUT"):return MatchActionType.END;
            default: return null;
        }
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