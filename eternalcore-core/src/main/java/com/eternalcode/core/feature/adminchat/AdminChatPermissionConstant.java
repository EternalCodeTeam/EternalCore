package com.eternalcode.core.feature.adminchat;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

final class AdminChatPermissionConstant {

    @PermissionDocs(
        name = "Admin Chat",
        permission = AdminChatPermissionConstant.ADMIN_CHAT_PERMISSION,
        description = "Allows the player to use admin chat commands and functionality."
    )
    static final String ADMIN_CHAT_PERMISSION = "eternalcore.adminchat";

    @PermissionDocs(
        name = "Admin Chat See",
        permission = AdminChatPermissionConstant.ADMIN_CHAT_SEE_PERMISSION,
        description = "Allows the player to see messages sent in admin chat by other players."
    )
    static final String ADMIN_CHAT_SEE_PERMISSION = "eternalcore.adminchat.see";
}
