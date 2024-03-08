package cps.handball.match;

import cps.handball.playermatchstats.PlayerMatchStats;
import jakarta.persistence.*;
import cps.handball.team.Team;

import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp matchTime;
    private boolean isFinished;
    private int homeTeamGoals;
    private int awayTeamGoals;

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team awayTeam;

    @OneToMany(mappedBy = "match")
    private Set<PlayerMatchStats> playerStats;

    // Constructors, getters, and setters
}
