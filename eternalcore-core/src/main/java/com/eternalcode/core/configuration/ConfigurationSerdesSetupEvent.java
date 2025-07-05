package com.eternalcode.core.configuration;

import com.eternalcode.core.publish.event.EternalCoreEvent;
import eu.okaeri.configs.serdes.SerdesRegistry;

public class ConfigurationSerdesSetupEvent implements EternalCoreEvent {

    private final SerdesRegistry registry;

    public ConfigurationSerdesSetupEvent(SerdesRegistry registry) {
        this.registry = registry;
    }

    public SerdesRegistry getRegistry() {
        return registry;
    }

}
