package com.eternalcode.core.feature.randomteleport.messages;

import com.eternalcode.multification.notice.Notice;

public interface RandomTeleportMessages {
    Notice randomTeleportStarted();
    Notice randomTeleportFailed();
    Notice teleportedToRandomLocation();
    Notice teleportedSpecifiedPlayerToRandomLocation();
    Notice randomTeleportDelay();
}
