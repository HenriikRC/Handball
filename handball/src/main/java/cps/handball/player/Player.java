package cps.handball.player;

import cps.handball.playeraction.PlayerAction;
import cps.handball.playermatchstats.PlayerMatchStats;
import jakarta.persistence.*;
import cps.handball.team.Team;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int playerNumber;

    private int totalGoals;
    private int totalMistakes;
    private int totalRescues;
    private double totalMep;
    private PositionType positionType;

    private String playerImageLink;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "player")
    private Set<PlayerMatchStats> matchStats;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PlayerAction> playerActions = new HashSet<>();

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

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getTotalGoals() {
        return totalGoals;
    }

    public void setTotalGoals(int totalGoals) {
        this.totalGoals = totalGoals;
    }

    public int getTotalMistakes() {
        return totalMistakes;
    }

    public void setTotalMistakes(int totalMistakes) {
        this.totalMistakes = totalMistakes;
    }

    public int getTotalRescues() {
        return totalRescues;
    }

    public void setTotalRescues(int totalRescues) {
        this.totalRescues = totalRescues;
    }

    public double getTotalMep() {
        return totalMep;
    }

    public void setTotalMep(double totalMep) {
        this.totalMep = totalMep;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<PlayerMatchStats> getMatchStats() {
        return matchStats;
    }

    public void setMatchStats(Set<PlayerMatchStats> matchStats) {
        this.matchStats = matchStats;
    }

    public Set<PlayerAction> getPlayerActions() {
        return playerActions;
    }

    public void setPlayerActions(Set<PlayerAction> playerActions) {
        this.playerActions = playerActions;
    }

    public void addPlayerAction(PlayerAction playerAction) {
        playerActions.add(playerAction);
        playerAction.setPlayer(this);
    }

    public String getPlayerImageLink() {
        return playerImageLink;
    }

    public void setPlayerImageLink(String playerImageLink) {
        this.playerImageLink = playerImageLink;
    }
}
