package midas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import midas.annotations.NoCommentsNeeded;

@NoCommentsNeeded
public class SteamPlayer {

    @JsonProperty("steamid")
    private long steamID;
    @JsonProperty("personaname")
    private String personaName;

    public long getSteamID() {
        return steamID;
    }

    public void setSteamID(long steamID) {
        this.steamID = steamID;
    }

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    @Override
    public String toString() {
        return "SteamPlayer{" +
                "steamID=" + steamID +
                ", personaName='" + personaName + '\'' +
                '}';
    }
}
