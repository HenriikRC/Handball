package cps.handball.player;

import cps.handball.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findPlayerByTeamAndName(Team team, String string);
}
