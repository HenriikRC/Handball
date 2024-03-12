package cps.handball.playeraction;

import cps.handball.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerActionRepository extends JpaRepository<PlayerAction, Long> {

    public List<PlayerAction> findPlayerActionByMatch(Match match);
}
