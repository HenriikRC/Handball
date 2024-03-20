package cps.handball.live;

import cps.handball.matchaction.MatchActionDTO;

public class MatchActionEvent {

    private final Long matchId;
    private final MatchActionDTO matchActionDTO;

    public MatchActionEvent(Long matchId, MatchActionDTO matchActionDTO) {
        this.matchId = matchId;
        this.matchActionDTO = matchActionDTO;
    }

    public Long getMatchId() {
        return matchId;
    }

    public MatchActionDTO getMatchAction() {
        return matchActionDTO;
    }
}
