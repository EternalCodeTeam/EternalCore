package com.eternalcode.core.feature.deathteleport.messages;

import com.eternalcode.multification.notice.Notice;

public interface DeathTeleportMessages {

    Notice deathTeleportStarted();

    Notice deathTeleportSuccess();

    Notice deathTeleportSelfEnabled();
    Notice deathTeleportSelfDisabled();

    Notice deathTeleportOtherEnabled();
    Notice deathTeleportOtherDisabled();
}
