package com.eternalcode.core.feature.warp.messages;

import com.eternalcode.multification.notice.Notice;

public interface WarpMessages {
    Notice warpAlreadyExists();
    Notice notExist();
    Notice create();
    Notice remove();
    Notice available();
    Notice itemAdded();
    Notice noWarps();
    Notice itemLimit();
    Notice noPermission();
    Notice addPermissions();
    Notice removePermission();
    Notice permissionDoesNotExist();
    Notice permissionAlreadyExist();
    Notice noPermissionsProvided();
    Notice missingWarpArgument();
    Notice missingPermissionArgument();
}
