package com.eternalcode.core.feature.vanish;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

public class VanishPermissionConstant {

    @PermissionDocs(
        name = "Vanish See",
        permission = VanishPermissionConstant.VANISH_SEE_PERMISSION,
        description = "Allows the player to see vanished players."
    )
    public static final String VANISH_SEE_PERMISSION = "eternalcore.vanish.see";

    public static final String VANISH_COMMAND_PERMISSION = "eternalcore.vanish.command";
    public static final String VANISH_COMMAND_PERMISSION_OTHER = "eternalcore.vanish.command.other";

    @PermissionDocs(
        name = "Vanish Join",
        permission = VanishPermissionConstant.VANISH_JOIN_PERMISSION,
        description = "Allows the player to join as vanished player."
    )
    public static final String VANISH_JOIN_PERMISSION = "eternalcore.vanish.join";

}
