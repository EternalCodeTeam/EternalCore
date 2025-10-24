package com.eternalcode.core.feature.back.messages;

import com.eternalcode.multification.notice.Notice;

public interface BackMessages {

    Notice lastLocationNotFound();

    Notice teleportedToLastTeleportLocation();
    Notice teleportedTargetPlayerToLastTeleportLocation();
    Notice teleportedToLastTeleportLocationByAdmin();

    Notice teleportedToLastDeathLocation();
    Notice teleportedTargetPlayerToLastDeathLocation();
    Notice teleportedToLastDeathLocationByAdmin();

}
