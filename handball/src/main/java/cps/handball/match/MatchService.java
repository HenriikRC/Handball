package cps.handball.match;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchService(MatchRepository matchRepository,
                        MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    @Transactional
    public void saveMatch(Match match) {
        matchRepository.save(match);
    }
    @Transactional
    public List<MatchDTO> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        return matches.stream().map(match -> new MatchDTO(
                match.getId(),
                match.getMatchTime().toString(),
                match.getHomeTeam().getName(),
                match.getAwayTeam().getName(),
                match.getHomeTeamGoals(),
                match.getAwayTeamGoals(),
                match.getPlayerAnalysisLink(),
                match.getMatchSiteLink(),
                match.isFinished()
        )).collect(Collectors.toList());
    }
    @Transactional
    public List<Match> findUnfinishedMatches() {
        return matchRepository.findByisFinishedFalse();
    }
    @Transactional
    public Optional<MatchDTO> getMatchById(Long id) {
        Optional<Match> matchOptional = matchRepository.findMatchById(id);
        return matchOptional.map(matchMapper::toDTO);
    }

    @Transactional
    public void markMatchAsFinished(Long matchId) {
        System.out.println("In matchService " + matchId);
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new EntityNotFoundException("Match not found with id " + matchId));
        match.setFinished(true);
        matchRepository.save(match);
    }

}