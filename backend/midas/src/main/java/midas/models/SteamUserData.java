package midas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import midas.annotations.NoCommentsNeeded;

@NoCommentsNeeded
public class SteamUserData {

    @JsonProperty("response")
    private SteamResponse steamResponse;

    public SteamResponse getSteamResponse() {
        return steamResponse;
    }

    public void setSteamResponse(SteamResponse steamResponse) {
        this.steamResponse = steamResponse;
    }
}
