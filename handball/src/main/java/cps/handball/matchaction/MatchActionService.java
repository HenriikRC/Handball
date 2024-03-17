package cps.handball.matchaction;

import cps.handball.match.Match;
import cps.handball.player.Player;
import cps.handball.team.Team;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchActionService {

    private final MatchActionRepository matchActionRepository;
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
        positionToMatchActionPositionTypeMap.put("Kontra 1. bølge", MatchActionPositionType.FIRST_WAVE);
        positionToMatchActionPositionTypeMap.put("Kontra 2. bølge", MatchActionPositionType.SECOND_WAVE);
        positionToMatchActionPositionTypeMap.put("Målvogter", MatchActionPositionType.GOALKEEPER);
    }


    public MatchActionService(MatchActionRepository matchActionRepository) {
        this.matchActionRepository = matchActionRepository;
    }

    public MatchAction recordPlayerAction(Team team, Player player, Player assistingPlayer,
                                          Team opponentTeam, Player goalKeeper, Player opponentPlayer,
                                          Match match, String matchTime,
                                          MatchActionType matchActionType, double mepRatingChange) {
        MatchAction matchAction = new MatchAction();
        matchAction.setTeam(team);
        matchAction.setPlayer(player);
        matchAction.setAssistingPlayer(assistingPlayer);
        matchAction.setOpponentTeam(opponentTeam);
        matchAction.setGoalKeeper(goalKeeper);
        matchAction.setOpponentPlayer(opponentPlayer);
        matchAction.setMatch(match);
        matchAction.setMatchTime(matchTime);
        matchAction.setActionType(matchActionType);
        matchAction.setMepRatingChange(mepRatingChange);
        return matchActionRepository.save(matchAction);
    }

    @Transactional
    public void save(Match match, String matchTime, Team attackingTeam, String position,
                     String playerName, MatchActionType matchActionType, String assistingPlayerName,
                     Team defendingTeam, String goalkeeperName) {
        MatchAction matchAction = new MatchAction();
        matchAction.setActionType(matchActionType);
        matchAction.setMatch(match);
        matchAction.setMatchTime(matchTime);
        matchAction.setTeam(attackingTeam);
        matchAction.setMatchActionPositionType(positionToMatchActionPositionTypeMap.get(position));


        match.getHomeTeam().getPlayers().stream()
                .filter(player -> player.getName().equalsIgnoreCase(playerName))
                .findFirst()
                .ifPresent(matchAction::setPlayer);


        if (assistingPlayerName != null) {
            attackingTeam.getPlayers().stream()
                    .filter(player -> player.getName().equalsIgnoreCase(assistingPlayerName))
                    .findFirst()
                    .ifPresent(matchAction::setAssistingPlayer);
        }

        matchAction.setOpponentTeam(defendingTeam);

        defendingTeam.getPlayers().stream()
                .filter(player -> player.getName().equalsIgnoreCase(goalkeeperName))
                .findFirst()
                .ifPresent(matchAction::setGoalKeeper);

        save(matchAction);
    }

    public MatchAction recordMatchAction(Match match, String matchTime,
                                         MatchActionType matchActionType){
        MatchAction matchAction = new MatchAction();
        matchAction.setMatch(match);
        matchAction.setMatchTime(matchTime);
        matchAction.setMatchActionType(matchActionType);
        return matchActionRepository.save(matchAction);
    }



    public List<MatchActionDTO> getPlayerActionsByMatchID(Match match) {
        List<MatchAction> matchActions = matchActionRepository.findPlayerActionByMatch(match);
        return matchActions.stream().map(playerAction -> new MatchActionDTO(
                playerAction.getId(),
                playerAction.getTeam(),
                playerAction.getPlayer(),
                playerAction.getAssistingPlayer(),
                playerAction.getOpponentTeam(),
                playerAction.getGoalKeeper(),
                playerAction.getOpponentPlayer(),
                playerAction.getMatch(),
                playerAction.getMatchTime(),
                playerAction.getActionType(),
                playerAction.getMatchActionPositionType(),
                playerAction.getMepRatingChange()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void save(MatchAction action) {
        matchActionRepository.save(action);
    }
}
