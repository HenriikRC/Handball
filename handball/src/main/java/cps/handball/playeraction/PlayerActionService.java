package cps.handball.playeraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerActionService {

    private final PlayerActionRepository playerActionRepository;

    @Autowired
    public PlayerActionService(PlayerActionRepository playerActionRepository) {
        this.playerActionRepository = playerActionRepository;
    }

    public PlayerAction recordPlayerAction(PlayerAction playerAction) {
        return playerActionRepository.save(playerAction);
    }
}
