package com.eternalcode.core.feature.teleport.tpa;

import java.time.Duration;

public interface TeleportRequestSettings {

    Duration tpaRequestExpire();

    Duration tpaTimer();

}
