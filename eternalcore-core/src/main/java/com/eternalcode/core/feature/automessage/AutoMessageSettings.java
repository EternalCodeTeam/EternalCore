package com.eternalcode.core.feature.automessage;

import java.time.Duration;

public interface AutoMessageSettings {

    boolean enabled();

    Duration interval();

    DrawMode drawMode();

    int minPlayers();

    enum DrawMode {
        RANDOM,
        SEQUENTIAL
    }

}
