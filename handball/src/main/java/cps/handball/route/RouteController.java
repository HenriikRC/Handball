package cps.handball.route;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String matchOverview() throws IOException{
        return convertToString("matchOverview");
    }


    public String convertToString(String htmlString) throws IOException {
        Resource resource = new ClassPathResource("public/" + htmlString + ".html");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(),
                StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
