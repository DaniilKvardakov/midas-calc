package midas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import midas.annotations.NoCommentsNeeded;

import java.util.List;

@NoCommentsNeeded
public class SteamResponse {

    @JsonProperty("players")
    List<SteamPlayer> players;

    public List<SteamPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<SteamPlayer> players) {
        this.players = players;
    }
}
