package com.eternalcode.core.feature.home;

import java.time.Duration;
import java.util.Map;

public interface HomesSettings {
    Map<String, Integer> maxHomes();
    Duration teleportTimeToHomes();
    String defaultHomeName();
}
