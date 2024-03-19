package cps.handball.route;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RouteController {

    @GetMapping("/matches")
    public String matchOverviewPage() {
        return "/matchOverview.html";
    }

    @GetMapping("/live/match/{matchId}")
    public String liveMatchPage(@PathVariable String matchId){
        System.out.println(matchId);
        return "/liveMatch.html";
    }

}