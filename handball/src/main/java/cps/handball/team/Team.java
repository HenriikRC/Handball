package cps.handball.team;

import cps.handball.player.Player;
import jakarta.persistence.*;
import cps.handball.match.Match;

import java.util.Set;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortName;
    private int wins;
    private int losses;
    private int ties;
    private int goalsTotal;
    private int goalsAgainstTotal;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private Set<Player> players;

    @OneToMany(mappedBy = "homeTeam")
    private Set<Match> homeMatches;

    @OneToMany(mappedBy = "awayTeam")
    private Set<Match> awayMatches;

    public Team(){}
    public Team (String name) {
        this.name = name;
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

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<Match> getHomeMatches() {
        return homeMatches;
    }

    public void setHomeMatches(Set<Match> homeMatches) {
        this.homeMatches = homeMatches;
    }

    public Set<Match> getAwayMatches() {
        return awayMatches;
    }

    public void setAwayMatches(Set<Match> awayMatches) {
        this.awayMatches = awayMatches;
    }
}
