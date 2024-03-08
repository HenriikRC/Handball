package cps.handball.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
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
}
