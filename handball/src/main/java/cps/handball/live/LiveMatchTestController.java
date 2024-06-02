package cps.handball.live;

import cps.handball.matchaction.MatchActionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/test/api/matchaction")
public class LiveMatchTestController {

    private final MatchActionEventPublisher eventPublisher;

    public LiveMatchTestController(MatchActionEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public void publishMatchAction(@RequestBody MatchActionDTO matchActionDTO) {
        // Publish event
        MatchActionEvent event = new MatchActionEvent(matchActionDTO.getMatch().getId(), matchActionDTO);
        eventPublisher.publishMatchActionEvent(event);
    }
}
