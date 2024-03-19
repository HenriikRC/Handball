package cps.handball.matchaction;

import cps.handball.match.MatchMapper;
import cps.handball.team.TeamMapper;
import cps.handball.player.PlayerMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchActionMapper {

    private final TeamMapper teamMapper;
    private final PlayerMapper playerMapper;
    private final MatchMapper matchMapper;

    public MatchActionMapper(TeamMapper teamMapper,
                             PlayerMapper playerMapper,
                             MatchMapper matchMapper) {
        this.teamMapper = teamMapper;
        this.playerMapper = playerMapper;
        this.matchMapper = matchMapper;
    }

    public MatchActionDTO toDTO(MatchAction matchAction) {
        MatchActionDTO dto = new MatchActionDTO();
        dto.setId(matchAction.getId());
        dto.setTeam(teamMapper.toDTO(matchAction.getTeam()));
        dto.setPlayer(playerMapper.toDTO(matchAction.getPlayer()));
        dto.setAssistingPlayer(playerMapper.toDTO(matchAction.getAssistingPlayer()));
        dto.setOpponentTeam(teamMapper.toDTO(matchAction.getOpponentTeam()));
        dto.setGoalKeeper(playerMapper.toDTO(matchAction.getGoalKeeper()));
        dto.setOpponentPlayer(playerMapper.toDTO(matchAction.getOpponentPlayer()));
        dto.setMatch(matchMapper.toDTO(matchAction.getMatch()));
        dto.setMatchTime(matchAction.getMatchTime());
        dto.setMatchActionType(matchAction.getMatchActionType());
        dto.setMatchActionPositionType(matchAction.getMatchActionPositionType());
        dto.setMepRatingChange(matchAction.getMepRatingChange());
        return dto;
    }

    public List<MatchActionDTO> toDTO(List<MatchAction> matchActions) {
        return matchActions.stream()
                .map(matchAction -> toDTO(matchAction))
                .collect(Collectors.toList());
    }

}