package cps.handball.matchaction;

import cps.handball.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchActionRepository extends JpaRepository<MatchAction, Long> {

    List<MatchAction> findPlayerActionByMatch(Match match);

    List<MatchAction> findMatchActionByMatchId(Long matchId);

    List<MatchAction> findMatchActionsByMatchIdOrderByMatchTimeAsc(Long matchId);

    boolean existsByMatchIdAndMatchTimeAndMatchActionType(Long matchId, String matchTime, MatchActionType matchActionType);

    boolean existsByMatchIdAndMatchActionType(Long matchId, MatchActionType matchActionType);

}
