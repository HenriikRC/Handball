package cps.handball.matchaction;

import cps.handball.match.Match;
import cps.handball.team.Team;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MatchActionService {

    private final MatchActionRepository matchActionRepository;
    private final MatchActionMapper matchActionMapper;
    private HashMap<String, MatchActionPositionType> positionToMatchActionPositionTypeMap = new HashMap<>();
    @PostConstruct
    public void initializeMap() {
        positionToMatchActionPositionTypeMap.put("Højre fløj", MatchActionPositionType.RIGHT_WING);
        positionToMatchActionPositionTypeMap.put("Højre back", MatchActionPositionType.RIGHT_BACK);
        positionToMatchActionPositionTypeMap.put("Streg", MatchActionPositionType.CENTER);
        positionToMatchActionPositionTypeMap.put("Playmaker", MatchActionPositionType.PLAYMAKER);
        positionToMatchActionPositionTypeMap.put("Venstre back", MatchActionPositionType.LEFT_BACK);
        positionToMatchActionPositionTypeMap.put("Venstre fløj", MatchActionPositionType.LEFT_WING);
        positionToMatchActionPositionTypeMap.put("Gennembrud", MatchActionPositionType.BREAKTHROUGH);
        positionToMatchActionPositionTypeMap.put("Straffemål", MatchActionPositionType.PENALTY);
        positionToMatchActionPositionTypeMap.put("Kontra 1. bølge", MatchActionPositionType.FIRST_WAVE);
        positionToMatchActionPositionTypeMap.put("Kontra 2. bølge", MatchActionPositionType.SECOND_WAVE);
        positionToMatchActionPositionTypeMap.put("Målvogter", MatchActionPositionType.GOALKEEPER);
    }

    public MatchActionService(MatchActionRepository matchActionRepository,
                              MatchActionMapper matchActionMapper) {
        this.matchActionRepository = matchActionRepository;
        this.matchActionMapper = matchActionMapper;
    }

    @Transactional
    public MatchAction save(Match match, String matchTime, Team attackingTeam, String position,
                     String playerName, MatchActionType matchActionType, String assistingPlayerName,
                     Team defendingTeam, String goalkeeperName) {
        MatchAction matchAction = new MatchAction();
        matchAction.setActionType(matchActionType);
        matchAction.setMatch(match);
        matchAction.setMatchTime(matchTime);
        matchAction.setTeam(attackingTeam);
        matchAction.setMatchActionPositionType(positionToMatchActionPositionTypeMap.get(position));

        attackingTeam.getPlayers().stream()
                .filter(player -> player.getName().toLowerCase().contains(playerName.toLowerCase()))
                .findFirst()
                .ifPresent(matchAction::setPlayer);

        if (assistingPlayerName != null) {
            attackingTeam.getPlayers().stream()
                    .filter(player -> player.getName().toLowerCase().contains(assistingPlayerName.toLowerCase()))
                    .findFirst()
                    .ifPresent(matchAction::setAssistingPlayer);
        }

        matchAction.setOpponentTeam(defendingTeam);

        defendingTeam.getPlayers().stream()
                .filter(player -> player.getName().equalsIgnoreCase(goalkeeperName))
                .findFirst()
                .ifPresent(matchAction::setGoalKeeper);

        return save(matchAction);
    }

    public List<MatchActionDTO> findAllMatchActions() {
        List<MatchAction> allMatchActions = matchActionRepository.findAll();
        return matchActionMapper.toDTO(allMatchActions);
    }

    public List<MatchActionDTO> findMatchActionsByMatchIdOrderByMatchTimeDesc(Long matchID) {
        List<MatchAction> matchActions = matchActionRepository.findMatchActionsByMatchIdOrderByMatchTimeAsc(matchID);
        return matchActionMapper.toDTO(matchActions);
    }

    @Transactional
    public MatchAction save(MatchAction action) {
        return matchActionRepository.save(action);
    }

    public boolean checkIfExists(Long matchId, String matchTime, MatchActionType matchActionType) {
        return matchActionRepository.existsByMatchIdAndMatchTimeAndMatchActionType(matchId, matchTime, matchActionType);
    }

    public boolean checkIfMatchActionTypeExistsForMatchId(Long matchId, MatchActionType matchActionType) {
        return matchActionRepository.existsByMatchIdAndMatchActionType(matchId, matchActionType);
    }

}