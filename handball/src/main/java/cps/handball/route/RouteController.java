package cps.handball.route;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Controller
public class RouteController {

    @GetMapping("/matches")
    @ResponseBody
    public String matchOverviewPage() throws IOException{
        return convertToString("matchOverview");
    }

    @GetMapping("/live/match/{matchId}")
    @ResponseBody
    public String liveMatchPage(@PathVariable("matchId") Long matchId) throws IOException{
        System.out.println(matchId);
        return convertToString("liveMatch");
    }


    public String convertToString(String htmlString) throws IOException {
        Resource resource = new ClassPathResource("public/" + htmlString + ".html");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(),
                StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
