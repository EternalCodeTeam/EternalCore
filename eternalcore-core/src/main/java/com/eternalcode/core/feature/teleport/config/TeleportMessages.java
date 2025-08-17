package com.eternalcode.core.feature.teleport.config;

import com.eternalcode.multification.notice.Notice;

public interface TeleportMessages {

    Notice countDown();

    Notice succes();

    Notice failureAfterChangeWorld();

    Notice failureAfterTakingDamage();

    Notice failtureAfterMoved();

}
