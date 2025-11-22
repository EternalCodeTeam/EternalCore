package com.eternalcode.core.feature.teleport.messages;

import com.eternalcode.multification.notice.Notice;

public interface TeleportMessages {
    // teleport
    Notice teleportedToPlayer();
    Notice teleportedPlayerToPlayer();
    Notice teleportedToHighestBlock();
    Notice teleportedAllToPlayer();

    // Task
    Notice teleportTimerFormat();
    Notice teleported();
    Notice teleporting();
    Notice teleportTaskCanceled();
    Notice teleportTaskAlreadyExist();

    // Coordinates XYZ
    Notice teleportedToCoordinates();
    Notice teleportedSpecifiedPlayerToCoordinates();
}
