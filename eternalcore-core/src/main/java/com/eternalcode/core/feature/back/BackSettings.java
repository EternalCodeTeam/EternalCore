package com.eternalcode.core.feature.back;

import java.time.Duration;

public interface BackSettings {

    Duration backTeleportTimer();

    Duration backLocationCacheDuration();
}
