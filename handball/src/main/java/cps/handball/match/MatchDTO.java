package cps.handball.match;

public class MatchDTO {
    private Long id;
    private String matchTime;
    private String homeTeamName;
    private String awayTeamName;
    private int homeTeamGoals;
    private int awayTeamGoals;
    private String playerAnalysisLink;

    public String getMatchSiteLink() {
        return matchSiteLink;
    }

    public void setMatchSiteLink(String matchSiteLink) {
        this.matchSiteLink = matchSiteLink;
    }

    private String matchSiteLink;


    public MatchDTO() {
    }

    public MatchDTO(Long id, String matchTime, String homeTeamName, String awayTeamName, int homeTeamGoals, int awayTeamGoals, String playerAnalysisLink, String matchSiteLink) {
        this.id = id;
        this.matchTime = matchTime;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.playerAnalysisLink = playerAnalysisLink;
        this.matchSiteLink = matchSiteLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public String getPlayerAnalysisLink() {
        return playerAnalysisLink;
    }

    public void setPlayerAnalysisLink(String playerAnalysisLink) {
        this.playerAnalysisLink = playerAnalysisLink;
    }
}
