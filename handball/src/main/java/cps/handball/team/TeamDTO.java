package cps.handball.team;

import cps.handball.player.PlayerDTO;

import java.util.List;
import java.util.Set;

public class TeamDTO {
    private Long id;
    private String name;
    private String shortName;
    private int wins;
    private int losses;
    private int ties;
    private int goalsTotal;
    private int goalsAgainstTotal;
    private List<PlayerDTO> players;

    public TeamDTO() {}
    public TeamDTO(Long id, String name, String shortName, int wins, int losses, int ties, int goalsTotal, int goalsAgainstTotal, List<PlayerDTO> players) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.goalsTotal = goalsTotal;
        this.goalsAgainstTotal = goalsAgainstTotal;
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    public int getGoalsTotal() {
        return goalsTotal;
    }

    public void setGoalsTotal(int goalsTotal) {
        this.goalsTotal = goalsTotal;
    }

    public int getGoalsAgainstTotal() {
        return goalsAgainstTotal;
    }

    public void setGoalsAgainstTotal(int goalsAgainstTotal) {
        this.goalsAgainstTotal = goalsAgainstTotal;
    }
    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
