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
    private int rescues;
    private double mep;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

}
