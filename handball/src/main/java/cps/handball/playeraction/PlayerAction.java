package cps.handball.playeraction;

import cps.handball.match.Match;
import cps.handball.player.Player;
import jakarta.persistence.*;

@Entity
public class PlayerAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Match match;

    private String matchTime;
    private ActionType actionType;
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
