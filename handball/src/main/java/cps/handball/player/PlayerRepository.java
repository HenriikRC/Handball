package cps.handball.player;

import cps.handball.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findPlayerByTeamAndName(Team team, String string);
}
