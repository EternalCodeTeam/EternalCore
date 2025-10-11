package com.eternalcode.core.feature.back.messages;

import com.eternalcode.multification.notice.Notice;

public interface BackMessages {

    Notice lastLocationNotFound();

    Notice teleportedToLastTeleportLocation();

    Notice teleportedOtherToLastTeleportLocation();

    Notice teleportedToLastDeathLocation();

    Notice teleportedOtherToLastDeathLocation();

}
