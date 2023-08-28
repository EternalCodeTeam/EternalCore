package com.eternalcode.core.feature.warp;

import java.time.Duration;
import java.util.Map;

public interface WarpSettings {

    Duration teleportationTimeToWarp();

    Map<String, Duration> teleportationTimesByPermission();
}
