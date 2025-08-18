package com.eternalcode.core.feature.teleport;

import java.time.Duration;
import java.util.Map;

public interface TeleportCommandSettings {

    boolean useDelay();

    Map<String, Duration> delayTiers();

    Duration defaultDelay();

    String bypassPermission();

}
