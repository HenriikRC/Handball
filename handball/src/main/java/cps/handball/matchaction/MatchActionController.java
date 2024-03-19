package cps.handball.matchaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/api/v1/matchactions")
public class MatchActionController {

    private final MatchActionService matchActionService;
    private final ConcurrentMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();


    public MatchActionController(MatchActionService matchActionService) {
        this.matchActionService = matchActionService;
    }

    @GetMapping
    public ResponseEntity<List<MatchActionDTO>> getAllMatchActions(){
        List<MatchActionDTO> matchActionDTOS = matchActionService.findAllMatchActions();
        return ResponseEntity.ok(matchActionDTOS);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<List<MatchActionDTO>> getAllMatchActionsForMatch(@PathVariable Long matchId){
        List<MatchActionDTO> matchActionDTOS = matchActionService.findMatchActionsByMatchIdOrderByMatchTimeDesc(matchId);
        return ResponseEntity.ok(matchActionDTOS);
    }

}
