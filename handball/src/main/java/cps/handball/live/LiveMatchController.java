package cps.handball.live;


import jakarta.persistence.Tuple;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LiveMatchController {
    private final ConcurrentMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping(value = "/api/v1/live/matchactions/{matchId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamMatchActions(@PathVariable Long matchId) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(matchId, emitter);
        emitter.onCompletion(() -> emitters.remove(matchId, emitter));
        emitter.onTimeout(() -> emitters.remove(matchId, emitter));
        return emitter;
    }

    @EventListener
    public void onMatchActionEvent(MatchActionEvent event) {
        Long matchId = event.getMatchId();
        Object matchAction = event.getMatchAction();

        SseEmitter emitter = emitters.get(matchId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(matchAction));
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }
    }

}