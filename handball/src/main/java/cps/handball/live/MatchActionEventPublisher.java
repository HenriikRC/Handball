package cps.handball.live;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchActionEventPublisher implements MatchActionPublisher {
    private final List<MatchActionObserver> observers = new ArrayList<>();
    public MatchActionEventPublisher(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanNamesForType(MatchActionObserver.class);
        for (String beanName : beanNames) {
            observers.add((MatchActionObserver) applicationContext.getBean(beanName));
        }
    }
    @Override
    public void addObserver(MatchActionObserver observer) {observers.add(observer);}
    @Override
    public void removeObserver(MatchActionObserver observer) {observers.remove(observer);}
    @Override
    public void notifyObservers(MatchActionEvent event) {
        for (MatchActionObserver observer : observers) {observer.onMatchActionEvent(event);}
    }
}