package midas.model;

import java.io.Serializable;
import java.time.Duration;

public class MidasData implements Serializable {

    private final Duration totalTimeOfMatch;
    private final Duration midasTime;
    private final Duration wastedTime;
    private Duration timeOfSellMidas;

    public MidasData(Duration totalTimeOfMatch, Duration midasTime, Duration timeOfSellMidas, Duration wastedTime) {
        this.totalTimeOfMatch = totalTimeOfMatch;
        this.midasTime = midasTime;
        this.timeOfSellMidas = timeOfSellMidas;
        this.wastedTime = wastedTime;
    }

    public void setTimeOfSellMidas(Duration timeOfSellMidas) {
        this.timeOfSellMidas = timeOfSellMidas;
    }

    public Duration getTotalTimeOfMatch() {
        return totalTimeOfMatch;
    }

    public Duration getMidasTime() {
        return midasTime;
    }

    public Duration getTimeOfSellMidas() {
        return timeOfSellMidas;
    }

    public Duration getWastedTime() {
        return wastedTime;
    }
}
