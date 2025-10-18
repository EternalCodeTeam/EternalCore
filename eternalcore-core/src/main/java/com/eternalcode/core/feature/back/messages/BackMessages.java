package com.eternalcode.core.feature.back.messages;

import com.eternalcode.multification.notice.Notice;

public interface BackMessages {

    Notice lastLocationNotFound();

    // Teleport do ostatniej lokalizacji teleportacji
    Notice teleportedToLastTeleportLocation();
    Notice teleportedTargetPlayerToLastTeleportLocation();
    Notice teleportedToLastTeleportLocationByAdmin();

    // Teleport do ostatniej lokalizacji śmierci
    Notice teleportedToLastDeathLocation();
    Notice teleportedTargetPlayerToLastDeathLocation();
    Notice teleportedToLastDeathLocationByAdmin();

}
