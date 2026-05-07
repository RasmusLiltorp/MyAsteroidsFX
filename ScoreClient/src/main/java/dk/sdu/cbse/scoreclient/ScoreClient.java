package dk.sdu.cbse.scoreclient;

import dk.sdu.cbse.common.services.IScoreService;
import org.springframework.web.client.RestTemplate;

public class ScoreClient implements IScoreService {

    private static final String SCORE_URL = "http://localhost:8080/score/add";

    private final RestTemplate rest = new RestTemplate();

    @Override
    public Integer addPoint() {
        try {
            return rest.postForObject(SCORE_URL, null, Integer.class);
        } catch (Exception e) {
            return null;
        }
    }
}
