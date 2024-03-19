package cps.handball.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Autowired
    public TeamService(TeamRepository teamRepository,
                       TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public Optional<Team> getTeam(long id){
        return teamRepository.findById(id);
    }

    public Team save(Team team){
        return teamRepository.save(team);
    }

    public Team findOrCreateByName(String name){
        return teamRepository.findByName(name)
                .orElseGet(() -> {
                    Team team = new Team();
                    team.setName(name);
                    return teamRepository.save(team);
                });
    }

    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .filter(Objects::nonNull)
                .map(teamMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TeamDTO> getTeamById(Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        return teamOptional.map(teamMapper::toDTO);
    }
}
