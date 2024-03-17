package cps.handball.match;

import cps.handball.matchaction.MatchAction;
import cps.handball.playermatchstats.PlayerMatchStats;
import jakarta.persistence.*;
import cps.handball.team.Team;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp matchTime;
    private boolean isFinished;
    private int homeTeamGoals;
    private int awayTeamGoals;

    public String getMatchSiteLink() {
        return matchSiteLink;
    }

    public void setMatchSiteLink(String matchSiteLink) {
        this.matchSiteLink = matchSiteLink;
    }

    private String matchSiteLink;

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team awayTeam;
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MatchAction> matchActions = new HashSet<>();

    @OneToMany(mappedBy = "match")
    private Set<PlayerMatchStats> playerStats;

    private String playerAnalysisLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Timestamp matchTime) {
        this.matchTime = matchTime;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Set<PlayerMatchStats> getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(Set<PlayerMatchStats> playerStats) {
        this.playerStats = playerStats;
    }

    public String getPlayerAnalysisLink() {
        return playerAnalysisLink;
    }

    public void setPlayerAnalysisLink(String playerAnalysisLink) {
        this.playerAnalysisLink = playerAnalysisLink;
    }

    public Set<MatchAction> getPlayerActions() {
        return matchActions;
    }

    public void setPlayerActions(Set<MatchAction> matchActions) {
        this.matchActions = matchActions;
    }

    public void addPlayerAction(MatchAction matchAction) {
        matchActions.add(matchAction);
        matchAction.setMatch(this);
    }
}
