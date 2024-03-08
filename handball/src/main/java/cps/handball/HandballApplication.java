package cps.handball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("cps.handball")
public class HandballApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandballApplication.class, args);
    }

}
