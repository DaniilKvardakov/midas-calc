package midas.model;


import lombok.Data;

import java.time.Duration;

@Data
public class MidasData {
    private Duration totalTimeOfMatch;
    private Duration midasTime;
    private int failMidasCast;
}
