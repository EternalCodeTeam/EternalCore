package com.eternalcode.core.feature.back.messages;

import com.eternalcode.multification.notice.Notice;

public interface BackMessages {

    Notice lastLocationNoExist();

    Notice noPermissionBackOnDeath();

    Notice targetNoPermissionBackOnDeath();

    Notice teleportedToLastLocation();

    Notice teleportedSpecifiedPlayerLastLocation();

}
