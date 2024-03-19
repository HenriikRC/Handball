package cps.handball.live;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MatchActionEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public MatchActionEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishMatchActionEvent(MatchActionEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
