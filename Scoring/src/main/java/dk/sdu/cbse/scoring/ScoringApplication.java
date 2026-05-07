package dk.sdu.cbse.scoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoringApplication {

    private int score = 0;

    public static void main(String[] args) {
        SpringApplication.run(ScoringApplication.class, args);
    }

    @GetMapping("/score")
    public int getScore() {
        return score;
    }

    @PostMapping("/score/add")
    public int add() {
        return ++score;
    }

    @PostMapping("/score/reset")
    public int reset() {
        score = 0;
        return score;
    }
}
