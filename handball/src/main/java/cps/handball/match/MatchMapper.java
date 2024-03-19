package cps.handball.match;

import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

    public MatchDTO toDTO(Match match) {
        if (match == null) {
            return null;
        }
        MatchDTO dto = new MatchDTO();
        dto.setId(match.getId());
        dto.setMatchTime(match.getMatchTime().toString());
        dto.setHomeTeamName(match.getHomeTeam().getName());
        dto.setAwayTeamName(match.getAwayTeam().getName());
        dto.setHomeTeamGoals(match.getHomeTeamGoals());
        dto.setAwayTeamGoals(match.getAwayTeamGoals());
        dto.setPlayerAnalysisLink(match.getPlayerAnalysisLink());
        dto.setMatchSiteLink(match.getMatchSiteLink());
        return dto;
    }
}
