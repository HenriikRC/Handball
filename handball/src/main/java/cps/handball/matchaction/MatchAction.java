package cps.handball.matchaction;

import cps.handball.match.Match;
import cps.handball.player.Player;
import cps.handball.team.Team;
import jakarta.persistence.*;

@Entity
public class MatchAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Team team;
    @ManyToOne
    private Player player;
    @ManyToOne
    private Player assistingPlayer;
    @ManyToOne
    private Team OpponentTeam;
    @ManyToOne
    private Player goalKeeper;
    @ManyToOne
    private Player opponentPlayer;
    @ManyToOne
    private Match match;
    private String matchTime;
    private MatchActionType matchActionType;
    private MatchActionPositionType matchActionPositionType;
    private double mepRatingChange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public MatchActionType getActionType() {
        return matchActionType;
    }

    public void setActionType(MatchActionType matchActionType) {
        this.matchActionType = matchActionType;
    }

    public double getMepRatingChange() {
        return mepRatingChange;
    }

    public void setMepRatingChange(double mepRatingChange) {
        this.mepRatingChange = mepRatingChange;
    }

    public Player getAssistingPlayer() {
        return assistingPlayer;
    }

    public void setAssistingPlayer(Player assistingPlayer) {
        this.assistingPlayer = assistingPlayer;
    }

    public Player getGoalKeeper() {
        return goalKeeper;
    }

    public void setGoalKeeper(Player goalKeeper) {
        this.goalKeeper = goalKeeper;
    }

    public MatchActionType getMatchActionType() {
        return matchActionType;
    }

    public void setMatchActionType(MatchActionType matchActionType) {
        this.matchActionType = matchActionType;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getOpponentTeam() {
        return OpponentTeam;
    }

    public void setOpponentTeam(Team opponentTeam) {
        OpponentTeam = opponentTeam;
    }

    public MatchActionPositionType getMatchActionPositionType() {
        return matchActionPositionType;
    }

    public void setMatchActionPositionType(MatchActionPositionType matchActionPositionType) {
        this.matchActionPositionType = matchActionPositionType;
    }
}
