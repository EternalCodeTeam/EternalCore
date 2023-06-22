package com.eternalcode.core.feature.automessage;

import java.time.Duration;

public interface AutoMessageSettings {

    Duration interval();

    DrawMode drawMode();

    enum DrawMode {
        RANDOM,
        SEQUENTIAL
    }

}
