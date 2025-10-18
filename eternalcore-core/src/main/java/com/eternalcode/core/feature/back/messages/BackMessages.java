package com.eternalcode.core.feature.back.messages;

import com.eternalcode.multification.notice.Notice;

public interface BackMessages {

    Notice lastLocationNotFound();

    Notice teleportedToLastTeleportLocation();

    Notice teleportedTargetToLastTeleportLocation();

    Notice teleportedToLastDeathLocation();

    Notice teleportedTargetToLastDeathLocation();

}
