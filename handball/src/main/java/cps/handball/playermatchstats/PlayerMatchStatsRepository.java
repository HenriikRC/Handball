package cps.handball.playermatchstats;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerMatchStatsRepository extends JpaRepository<PlayerMatchStats, Long> {
}
