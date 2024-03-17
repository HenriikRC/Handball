package cps.handball.player;

import cps.handball.team.Team;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    @Transactional
    public void savePlayer(Player player){
        playerRepository.save(player);
    }

    @Transactional
    public Player findPlayerByTeamAndName(Team team, String name) {
        return playerRepository.findPlayerByTeamAndName(team, name);
    }
}
