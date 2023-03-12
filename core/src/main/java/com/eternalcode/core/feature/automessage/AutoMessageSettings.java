package com.eternalcode.core.feature.automessage;

public interface AutoMessageSettings {

    int interval();

    DrawMode drawMode();

    enum DrawMode {
        RANDOM,
        SEQUENTIAL
    }
}
