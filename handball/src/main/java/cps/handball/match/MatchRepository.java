package cps.handball.match;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByisFinishedFalse();

    Match findMatchById(Long id);
}
