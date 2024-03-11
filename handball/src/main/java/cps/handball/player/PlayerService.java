package cps.handball.player;

import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public void savePlayer(Player player){
        playerRepository.save(player);
    }
}
