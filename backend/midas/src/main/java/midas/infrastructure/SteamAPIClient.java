package midas.infrastructure;

import jakarta.annotation.Nullable;
import midas.annotations.NoCommentsNeeded;
import midas.models.SteamUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@NoCommentsNeeded
public class SteamAPIClient {

    /**
     * Первая часть url для запроса к steam api. (Когда нужно получить имя игрока по account id)
     */
    @Value("${midas.steam.first.api.url}")
    private String firstHalfOfSteamURL;
    /**
     * Вторая часть url для запроса к steam api. (Когда нужно получить имя игрока по account id)
     */
    @Value("${midas.steam.second.api.url}")
    private String secondHalfOfSteamURL;
    /**
     * Ключ Steam API.
     */
    @Value("${midas.steam.api.key}")
    private String steamApiKey;

    @Value("${midas.steam.url.converter}")
    private long steam64base;


    private final RestTemplate restTemplate;

    @Autowired
    public SteamAPIClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Nullable
    public SteamUserData getSteamUserData(long accountId) {

        SteamUserData steamUserData;
        try {
            steamUserData = restTemplate
                    .getForEntity(
                            firstHalfOfSteamURL + steamApiKey + secondHalfOfSteamURL + accountIdToSteam64(accountId),
                            SteamUserData.class
                    )
                    .getBody();
            System.out.println("SteamUserData: " + steamUserData);
            return steamUserData;
        } catch (RestClientException restClientException) {
            return null;
        }
    }

    private long accountIdToSteam64(long accountId) {
        return steam64base + accountId;
    }


}
