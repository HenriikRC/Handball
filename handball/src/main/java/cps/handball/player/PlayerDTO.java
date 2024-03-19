package cps.handball.player;

public class PlayerDTO {
    private Long id;
    private String name;
    private int playerNumber;
    private int totalGoals;
    private int totalMistakes;
    private int totalRescues;
    private double totalMep;
    private PositionType positionType;
    private String playerImageLink;

    public PlayerDTO() {}

    public PlayerDTO(Long id, String name, int playerNumber, int totalGoals, int totalMistakes,
                     int totalRescues, double totalMep, PositionType positionType,
                     String playerImageLink) {
        this.id = id;
        this.name = name;
        this.playerNumber = playerNumber;
        this.totalGoals = totalGoals;
        this.totalMistakes = totalMistakes;
        this.totalRescues = totalRescues;
        this.totalMep = totalMep;
        this.positionType = positionType;
        this.playerImageLink = playerImageLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getTotalGoals() {
        return totalGoals;
    }

    public void setTotalGoals(int totalGoals) {
        this.totalGoals = totalGoals;
    }

    public int getTotalMistakes() {
        return totalMistakes;
    }

    public void setTotalMistakes(int totalMistakes) {
        this.totalMistakes = totalMistakes;
    }

    public int getTotalRescues() {
        return totalRescues;
    }

    public void setTotalRescues(int totalRescues) {
        this.totalRescues = totalRescues;
    }

    public double getTotalMep() {
        return totalMep;
    }

    public void setTotalMep(double totalMep) {
        this.totalMep = totalMep;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public String getPlayerImageLink() {
        return playerImageLink;
    }

    public void setPlayerImageLink(String playerImageLink) {
        this.playerImageLink = playerImageLink;
    }
}
