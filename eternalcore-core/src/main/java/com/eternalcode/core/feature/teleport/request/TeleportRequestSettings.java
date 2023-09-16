package com.eternalcode.core.feature.teleport.request;

import java.time.Duration;

public interface TeleportRequestSettings {

    Duration teleportExpire();

    Duration teleportTime();

}
