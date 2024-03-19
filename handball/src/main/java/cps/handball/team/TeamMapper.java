package cps.handball.team;

import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public TeamDTO toDTO(Team team) {
        if (team == null) {
            return null;
        }
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setShortName(team.getShortName());
        teamDTO.setWins(team.getWins());
        teamDTO.setLosses(team.getLosses());
        teamDTO.setTies(team.getTies());
        teamDTO.setGoalsTotal(team.getGoalsTotal());
        teamDTO.setGoalsAgainstTotal(team.getGoalsAgainstTotal());
        return teamDTO;
    }
}
