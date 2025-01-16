package midas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import midas.annotations.NoCommentsNeeded;

import java.util.List;

@NoCommentsNeeded
public class Match {

    @JsonProperty("players")
    private List<Player> players;

    /**
     * Метод проверяющий наличие "кастов" у игроков
     * @return пропарсен ли матч.
     */
    public boolean isParsed() {
        boolean status = false;
        for (Player player : this.getPlayers())
            status = !(player.getItemUses() == null);
        return status;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "\nMatch {" +
                "\n players = " + players +
                "\n}";
    }
}
