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

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;

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
                String playerImageUrl = playerEl.select(".team__player__left .team__player__img")
                        .attr("src");
                Player player = parsePlayerInfo(playerInfo, team);
                if (player != null) {
                    playerService.savePlayer(player);
                    downloadAndSavePlayerImage(playerImageUrl, player);
                } else {
                    System.out.println("Skipping non-player: " + playerInfo);
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

    private void downloadAndSavePlayerImage(String playerImageUrl, Player player) {
        try {
            String fullImageUrl = "https://www.tophaandbold.dk" + playerImageUrl;
            URL imageUrl = new URL(fullImageUrl);
            String extension = playerImageUrl.substring(playerImageUrl.lastIndexOf("."));
            String basePath = System.getProperty("user.dir");
            String relativePath = "src/main/resources/public/assets/images/player/";
            String fileName = player.getId() + extension;
            String localFilePath = Paths.get(basePath, relativePath, fileName).toString();
            File directory = new File(Paths.get(basePath, relativePath).toString());
            if (!directory.exists()){
                directory.mkdirs();
            }
            try (InputStream in = imageUrl.openStream(); OutputStream out = new FileOutputStream(localFilePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            System.out.println("Downloaded image to " + localFilePath);
            String playerImageLink = "http://localhost:8080/assets/images/player/" + fileName;
            updatePlayerImageLink(player, playerImageLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private void updatePlayerImageLink(Player player, String playerImageLink) {
            player.setPlayerImageLink(playerImageLink);
            playerService.savePlayer(player);
        }
}
