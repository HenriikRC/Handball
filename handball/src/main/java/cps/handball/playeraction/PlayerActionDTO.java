package cps.handball.playeraction;

import cps.handball.match.Match;
import cps.handball.player.Player;

public class PlayerActionDTO {

    private Long id;
    private Player player;
    private Player opponentPlayer;
    private Match match;
    private String matchTime;
    private ActionType actionType;
    private double mepRatingChange;

    public PlayerActionDTO(Long id, Player player, Player opponentPlayer, Match match, String matchTime, ActionType actionType, double mepRatingChange) {
        this.id = id;
        this.player = player;
        this.opponentPlayer = opponentPlayer;
        this.match = match;
        this.matchTime = matchTime;
        this.actionType = actionType;
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

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public double getMepRatingChange() {
        return mepRatingChange;
    }

    public void setMepRatingChange(double mepRatingChange) {
        this.mepRatingChange = mepRatingChange;
    }
}
