package com.eternalcode.core.feature.teleportoffline;

import com.eternalcode.multification.notice.Notice;

public interface TeleportOfflineMessages {

    Notice offlinePlayerNotPlayedBefore();
    Notice lastLocationNotFound();
    Notice teleportedToPlayerLastLocation();
}
