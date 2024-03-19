package cps.handball.matchaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cps.handball.match.Match;
import cps.handball.match.MatchDTO;
import cps.handball.player.Player;
import cps.handball.player.PlayerDTO;
import cps.handball.team.Team;
import cps.handball.team.TeamDTO;

public class MatchActionDTO {

    private Long id;
    private TeamDTO team;
    private PlayerDTO player;
    private PlayerDTO assistingPlayer;
    private TeamDTO opponentTeam;
    private PlayerDTO goalKeeper;
    private PlayerDTO opponentPlayer;
    private MatchDTO match;
    private String matchTime;
    private MatchActionType matchActionType;
    private MatchActionPositionType matchActionPositionType;
    private double mepRatingChange;

    public MatchActionDTO() {

    }

    public MatchActionDTO(Long id, TeamDTO team, PlayerDTO player, PlayerDTO assistingPlayer,
                          TeamDTO opponentTeam, PlayerDTO goalKeeper, PlayerDTO opponentPlayer,
                          MatchDTO match, String matchTime,
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

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    public PlayerDTO getAssistingPlayer() {
        return assistingPlayer;
    }

    public void setAssistingPlayer(PlayerDTO assistingPlayer) {
        this.assistingPlayer = assistingPlayer;
    }

    public TeamDTO getOpponentTeam() {
        return opponentTeam;
    }

    public void setOpponentTeam(TeamDTO opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public PlayerDTO getGoalKeeper() {
        return goalKeeper;
    }

    public void setGoalKeeper(PlayerDTO goalKeeper) {
        this.goalKeeper = goalKeeper;
    }

    public PlayerDTO getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(PlayerDTO opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }

    public MatchDTO getMatch() {
        return match;
    }

    public void setMatch(MatchDTO match) {
        this.match = match;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public MatchActionType getMatchActionType() {
        return matchActionType;
    }

    public void setMatchActionType(MatchActionType matchActionType) {
        this.matchActionType = matchActionType;
    }

    public MatchActionPositionType getMatchActionPositionType() {
        return matchActionPositionType;
    }

    public void setMatchActionPositionType(MatchActionPositionType matchActionPositionType) {
        this.matchActionPositionType = matchActionPositionType;
    }

    public double getMepRatingChange() {
        return mepRatingChange;
    }

    public void setMepRatingChange(double mepRatingChange) {
        this.mepRatingChange = mepRatingChange;
    }
}
