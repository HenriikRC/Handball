package cps.handball.live;

public class MatchActionEvent {

    private final Long matchId;
    private final Object matchAction;

    public MatchActionEvent(Long matchId, Object matchAction) {
        this.matchId = matchId;
        this.matchAction = matchAction;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Object getMatchAction() {
        return matchAction;
    }
}

