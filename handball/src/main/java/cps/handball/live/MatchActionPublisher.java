package cps.handball.live;

public interface MatchActionPublisher {
    void addObserver(MatchActionObserver observer);
    void removeObserver(MatchActionObserver observer);
    void notifyObservers(MatchActionEvent event);
}