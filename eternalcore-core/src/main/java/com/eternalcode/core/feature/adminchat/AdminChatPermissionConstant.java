package com.eternalcode.core.feature.adminchat;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

public class AdminChatPermissionConstant {
    @PermissionDocs(
        name = "Admin Chat see",
        permission = AdminChatPermissionConstant.ADMIN_CHAT_SEE_PERMISSION,
        description = "Allows the player to see messages sent in admin chat by other players."
    )
    public static final String ADMIN_CHAT_SEE_PERMISSION = "eternalcore.adminchat.see";

}
