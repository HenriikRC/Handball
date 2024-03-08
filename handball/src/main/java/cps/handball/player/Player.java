package cps.handball.player;

import cps.handball.playermatchstats.PlayerMatchStats;
import jakarta.persistence.*;
import cps.handball.team.Team;

import java.util.Set;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalGoals;
    private int totalMistakes;
    private int totalRescues;
    private double totalMep;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "player")
    private Set<PlayerMatchStats> matchStats;

}
