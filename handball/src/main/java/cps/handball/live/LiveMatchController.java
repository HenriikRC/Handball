package cps.handball.live;

import cps.handball.matchaction.MatchActionDTO;
import cps.handball.matchaction.MatchActionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LiveMatchController {

    private final MatchActionService matchActionService;

    public LiveMatchController(MatchActionService matchActionService) {
        this.matchActionService = matchActionService;
    }

    @GetMapping("/live/match/{matchId}")
    public String liveMatchPage(@PathVariable("matchId") Long matchId, Model model) {
        System.out.println(matchId);
        List<MatchActionDTO> playerActions = matchActionService.findMatchActionsByMatchIdOrderByMatchTimeDesc(matchId);
        model.addAttribute("matchActions", playerActions);
        return "liveMatch";
    }
}