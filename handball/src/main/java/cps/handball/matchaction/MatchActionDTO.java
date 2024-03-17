package cps.handball.matchaction;

import cps.handball.match.Match;
import cps.handball.player.Player;
import cps.handball.team.Team;

public class MatchActionDTO {

    private Long id;
    private Team team;
    private Player player;
    private Player assistingPlayer;
    private Team opponentTeam;
    private Player goalKeeper;
    private Player opponentPlayer;
    private Match match;
    private String matchTime;
    private MatchActionType matchActionType;
    private MatchActionPositionType matchActionPositionType;
    private double mepRatingChange;

    public MatchActionDTO(Long id, Team team, Player player, Player assistingPlayer,
                          Team opponentTeam, Player goalKeeper, Player opponentPlayer,
                          Match match, String matchTime,
                          MatchActionType matchActionType, MatchActionPositionType matchActionPositionType,
                          double mepRatingChange) {
        this.id = id;
        this.team = team;
        this.player = player;
        this.assistingPlayer = assistingPlayer;
        this.opponentTeam = opponentTeam;
        this.goalKeeper = goalKeeper;
        this.opponentPlayer = opponentPlayer;
        this.match = match;
        this.matchTime = matchTime;
        this.matchActionType = matchActionType;
        this.matchActionPositionType = matchActionPositionType;
        this.mepRatingChange = mepRatingChange;
    }

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
        return opponentTeam;
    }

    public void setOpponentTeam(Team opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public MatchActionPositionType getMatchActionPositionType() {
        return matchActionPositionType;
    }

    public void setMatchActionPositionType(MatchActionPositionType matchActionPositionType) {
        this.matchActionPositionType = matchActionPositionType;
    }
}
