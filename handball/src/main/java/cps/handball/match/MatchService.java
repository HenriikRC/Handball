package cps.handball.match;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Formattable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional
    public void saveMatch(Match match) {
        matchRepository.save(match);
    }

    public List<MatchDTO> getAllMatchDTOs() {
        List<Match> matches = matchRepository.findAll();
        return matches.stream().map(match -> new MatchDTO(
                match.getId(),
                match.getMatchTime().toString(),
                match.getHomeTeam().getName(),
                match.getAwayTeam().getName(),
                match.getHomeTeamGoals(),
                match.getAwayTeamGoals(),
                match.getPlayerAnalysisLink(),
                match.getMatchSiteLink()
        )).collect(Collectors.toList());

    }
}
