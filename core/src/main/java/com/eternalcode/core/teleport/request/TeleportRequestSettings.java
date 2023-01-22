package com.eternalcode.core.teleport.request;

import java.time.Duration;

public interface TeleportRequestSettings {

    Duration teleportExpire();

    Duration teleportTime();

}
