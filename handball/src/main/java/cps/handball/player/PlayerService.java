package cps.handball.player;

import cps.handball.team.Team;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository,
                         PlayerMapper playerMapper){
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Transactional
    public void savePlayer(Player player){
        playerRepository.save(player);
    }

    public List<PlayerDTO> getAllPlayers(){
        return playerRepository.findAll()
                .stream()
                .map(playerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PlayerDTO> getPlayerById(Long id){
        Optional<Player> playerOptional = playerRepository.findById(id);
        return playerOptional.map(playerMapper::toDTO);
    }

    public Optional<PlayerDTO> findPlayerByTeamAndName(Team team, String name) {
        Optional<Player> playerOptional = playerRepository.findPlayerByTeamAndName(team, name);
        return playerOptional.map(playerMapper::toDTO);
    }
}
