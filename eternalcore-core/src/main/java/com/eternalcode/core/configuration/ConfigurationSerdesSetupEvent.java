package com.eternalcode.core.configuration;

import com.eternalcode.core.publish.event.EternalCoreEvent;
import eu.okaeri.configs.serdes.SerdesRegistry;

public record ConfigurationSerdesSetupEvent(SerdesRegistry registry) implements EternalCoreEvent {

}
