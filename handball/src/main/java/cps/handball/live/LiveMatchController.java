package cps.handball.live;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
public class LiveMatchController implements MatchActionObserver {
    private static final Logger logger = LoggerFactory.getLogger(LiveMatchController.class);
    private final ConcurrentMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping(value = "/api/v1/live/matchactions/{matchId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamMatchActions(@PathVariable Long matchId) {
        SseEmitter emitter = new SseEmitter(300_000L);
        emitters.put(matchId, emitter);
        emitter.onCompletion(() -> emitters.remove(matchId, emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(matchId, emitter);
        });
        return emitter;
    }

    @Override
    public void onMatchActionEvent(MatchActionEvent event) {
        Long matchId = event.getMatchId();
        Object matchAction = event.getMatchAction();
        for (Map.Entry<Long, SseEmitter> entry : emitters.entrySet()) {
            SseEmitter emitter = emitters.get(matchId);
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().data(matchAction));
                } catch (Exception e) {
                    logger.error("Error sending SSE for match ID: {}", matchId, e);
                    emitter.completeWithError(e);
                }
            }
        }
    }
}