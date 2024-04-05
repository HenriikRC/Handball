package cps.handball.season;

import cps.handball.match.Match;
import cps.handball.team.Team;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


@Entity
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp seasonStartDate;
    private Timestamp seasonEndDate;

    @ManyToMany
    private Set<Team> teams;

    @ManyToMany
    private Set<Match> matches;

    public Season() {
    }

    public Season(Timestamp seasonStartDate,
                  Timestamp seasonEndDate) {
        this.seasonStartDate = seasonStartDate;
        this.seasonEndDate = seasonEndDate;
    }

    public Season(Timestamp seasonStartDate,
                  Timestamp seasonEndDate,
                  Set<Team> teams,
                  Set<Match> matches) {
        this.seasonStartDate = seasonStartDate;
        this.seasonEndDate = seasonEndDate;
        this.teams = teams;
        this.matches = matches;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getSeasonStartDate() {
        return seasonStartDate;
    }

    public void setSeasonStartDate(Timestamp seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public Timestamp getSeasonEndDate() {
        return seasonEndDate;
    }

    public void setSeasonEndDate(Timestamp seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}