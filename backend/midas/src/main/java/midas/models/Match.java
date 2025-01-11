package midas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import midas.annotations.NoCommentsNeeded;

import java.util.List;

@NoCommentsNeeded
public class Match {

    @JsonProperty("players")
    private List<Player> players;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Match{" +
                "players=" + players +
                '}';
    }
}
