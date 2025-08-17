package com.eternalcode.core.feature.teleport.messages;

import com.eternalcode.multification.notice.Notice;

public interface TeleportOfflineMessages {

    Notice offlinePlayerNotPlayedBefore();
    Notice lastLocationNotFound();
    Notice teleportedToPlayerLastLocation();
}
