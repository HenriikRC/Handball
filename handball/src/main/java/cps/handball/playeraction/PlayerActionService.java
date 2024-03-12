package cps.handball.playeraction;

import cps.handball.match.Match;
import cps.handball.player.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerActionService {

    private final PlayerActionRepository playerActionRepository;

    public PlayerActionService(PlayerActionRepository playerActionRepository) {
        this.playerActionRepository = playerActionRepository;
    }

    public PlayerAction recordPlayerAction(Player player, Player opponentPlayer,
                                           ActionType actionType, Match match,
                                           String matchTime, double mepRatingChange) {
        PlayerAction playerAction = new PlayerAction();
        playerAction.setPlayer(player);
        playerAction.setOpponentPlayer(opponentPlayer);
        playerAction.setActionType(actionType);
        playerAction.setMatch(match);
        playerAction.setMatchTime(matchTime);
        playerAction.setMepRatingChange(mepRatingChange);
        return playerActionRepository.save(playerAction);
    }

    public List<PlayerActionDTO> getPlayerActionsByMatchID(Match match) {
        List<PlayerAction> playerActions = playerActionRepository.findPlayerActionByMatch(match);
        return playerActions.stream().map(playerAction -> new PlayerActionDTO(
                playerAction.getId(),
                playerAction.getPlayer(),
                playerAction.getOpponentPlayer(),
                playerAction.getMatch(),
                playerAction.getMatchTime(),
                playerAction.getActionType(),
                playerAction.getMepRatingChange()
        )).collect(Collectors.toList());
    }
}
