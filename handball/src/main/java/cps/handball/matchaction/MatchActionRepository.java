package cps.handball.matchaction;

import cps.handball.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchActionRepository extends JpaRepository<MatchAction, Long> {

    public List<MatchAction> findPlayerActionByMatch(Match match);

    List<MatchAction> findMatchActionByMatchId(Long matchId);

    List<MatchAction> findMatchActionsByMatchIdOrderByMatchTimeDesc(Long matchId);
}
