package cps.handball.player;

import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDTO toDTO(Player player) {
        if (player == null) {
            return null;
        }
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setPlayerNumber(player.getPlayerNumber());
        playerDTO.setTotalGoals(player.getTotalGoals());
        playerDTO.setTotalMistakes(player.getTotalMistakes());
        playerDTO.setTotalRescues(player.getTotalRescues());
        playerDTO.setTotalMep(player.getTotalMep());
        playerDTO.setPositionType(player.getPositionType());
        playerDTO.setPlayerImageLink(player.getPlayerImageLink());
        return playerDTO;
    }
}
