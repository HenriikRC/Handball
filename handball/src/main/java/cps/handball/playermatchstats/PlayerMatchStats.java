package cps.handball.playermatchstats;

import jakarta.persistence.*;
import cps.handball.match.Match;
import cps.handball.player.Player;

@Entity
public class PlayerMatchStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int goals;
    private int mistakes;
    private int rescues; // Assuming this is only for goalkeepers in a match
    private double mep; // Most Effective Player value for the match

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    // Constructors, getters, and setters
}
