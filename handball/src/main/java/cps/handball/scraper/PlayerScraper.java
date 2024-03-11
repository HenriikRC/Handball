package cps.handball.scraper;

import cps.handball.player.Player;
import cps.handball.player.PlayerService;
import cps.handball.player.PositionType;
import cps.handball.team.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PlayerScraper {

    private final PlayerService playerService;

    @Autowired
    public PlayerScraper(PlayerService playerService){
        this.playerService = playerService;
    }

    public void scrapePlayers(String playersPageUrl, Team team) {
        try {
            Document doc = Jsoup.connect(playersPageUrl).get();
            Elements playerElements = doc.select(".team__player__content");
            for (Element playerEl : playerElements) {
                String playerInfo = playerEl.select(".team__player__name").text() + " " +
                        playerEl.select(".team__player__name__position").text();

                Player player = parsePlayerInfo(playerInfo, team);


                if (player != null) {
                    playerService.savePlayer(player);
                } else {

                    System.out.println("Skipping non-player entry or unable to parse player from: " + playerInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Player parsePlayerInfo(String playerInfo, Team team) {

        if (!playerInfo.contains("#")) {
            System.out.println("Skipping non-player entry: " + playerInfo);
            return null;
        }

        String[] parts = playerInfo.split(" #");
        if (parts.length < 2 || !parts[1].contains(" ")) {
            System.out.println("Invalid player format: " + playerInfo);
            return null;
        }

        String name = parts[0].trim();
        String[] numberAndPosition = parts[1].split(" ", 2);
        int playerNumber = Integer.parseInt(numberAndPosition[0]);
        String positionString = numberAndPosition[1].trim();

        Player player = new Player();
        player.setName(name);
        player.setPlayerNumber(playerNumber);
        player.setPositionType(mapPositionStringToEnum(positionString));
        player.setTeam(team);

        return player;
    }



    private PositionType mapPositionStringToEnum(String positionString) {
        return switch (positionString) {
            case "Målvogter" -> PositionType.KEEPER;
            case "Playmaker" -> PositionType.PLAYMAKER;
            case "Højre fløj" -> PositionType.RIGHT_WING;
            case "Højre back" -> PositionType.RIGHT_BACK;
            case "Venstre fløj" -> PositionType.LEFT_WING;
            case "Venstre back" -> PositionType.LEFT_BACK;
            case "Stregspiller" -> PositionType.CENTER;
            default -> throw new IllegalArgumentException("Unknown position: " + positionString);
        };
    }
}
