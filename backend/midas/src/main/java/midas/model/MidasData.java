package midas.model;

import java.time.Duration;


public class MidasData {

    private Duration totalTimeOfMatch;
    private Duration midasTime;
    private Duration timeOfSellMidas;
    private int failMidasCast;

    public Duration getTotalTimeOfMatch() {
        return totalTimeOfMatch;
    }

    public void setTotalTimeOfMatch(Duration totalTimeOfMatch) {
        this.totalTimeOfMatch = totalTimeOfMatch;
    }

    public Duration getMidasTime() {
        return midasTime;
    }

    public void setMidasTime(Duration midasTime) {
        this.midasTime = midasTime;
    }

    public Duration getTimeOfSellMidas() {
        return timeOfSellMidas;
    }

    public void setTimeOfSellMidas(Duration timeOfSellMidas) {
        this.timeOfSellMidas = timeOfSellMidas;
    }

    public int getFailMidasCast() {
        return failMidasCast;
    }

    public void setFailMidasCast(int failMidasCast) {
        this.failMidasCast = failMidasCast;
    }
}
