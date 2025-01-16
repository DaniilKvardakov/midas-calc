package midas.infrastructure;

import jakarta.annotation.Nullable;
import midas.annotations.NoCommentsNeeded;
import midas.models.Match;
import midas.models.ParsingProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@NoCommentsNeeded
public class OpenDotaAPIClient {


    /**
     * URL для запроса парсинга матча.
     */
    @Value("${dota.api.parsing.url}")
    private String dotaApiParsingUrl;
    /**
     * URL для запроса к dota open api.
     */
    @Value("${dota.api.url}")
    private String dotaApiUrl;

    private final RestTemplate restTemplate;


    @Autowired
    public OpenDotaAPIClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Nullable
    public ParsingProcess getParsingProcessById(long matchId){
        try {
            return restTemplate.postForEntity(dotaApiParsingUrl + matchId, null, ParsingProcess.class).getBody();
        } catch (RestClientException restClientException) {
            return null;
        }
    }

    @Nullable
    public Match getMatchById(long matchId){
        try {
            return restTemplate.getForEntity(dotaApiUrl + matchId, Match.class).getBody();
        } catch (RestClientException restClientException) {
            return null;
        }
    }


}
