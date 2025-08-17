package com.eternalcode.core.feature.teleport.config;

import com.eternalcode.multification.notice.Notice;

public interface TeleportMessages {

    Notice countDown();

    Notice start();

    Notice succes();

    Notice failureAfterChangeWorld();
    Notice failureAfterTakingDamage();
    Notice failtureAfterMoved();
    Notice failtureAlreadyInTeleport();

}
