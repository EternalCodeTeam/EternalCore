package com.eternalcode.core.feature.jail;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

public class JailPermissionConstant {

    @PermissionDocs(
        name = "Jail Command",
        permission = JailPermissionConstant .JAIL_COMMAND_PERMISSION,
        description = "Permission allows to use jail commands."
    )
    public static final String JAIL_COMMAND_PERMISSION = "eternalcore.jail";

    @PermissionDocs(
        name = "Jail Bypass",
        permission = JailPermissionConstant.JAIL_BYPASS_PERMISSION,
        description = "Permission allows to bypass jail punishment."
    )
    public static final String JAIL_BYPASS_PERMISSION = "eternalcore.jail.bypass";

    @PermissionDocs(
        name = "Jail Detain",
        permission = JailPermissionConstant.JAIL_DETAIN_PERMISSION,
        description = "Allows to detain players in jail."
    )
    public static final String JAIL_DETAIN_PERMISSION = "eternalcore.jail.detain";

    @PermissionDocs(
        name = "Jail Release",
        permission = JailPermissionConstant.JAIL_RELEASE_PERMISSION,
        description = "Allows to release players from jail."
    )
    public static final String JAIL_RELEASE_PERMISSION = "eternalcore.jail.release";

    @PermissionDocs(
        name = "Jail List",
        permission = JailPermissionConstant.JAIL_LIST_PERMISSION,
        description = "Allows to list all jailed players."
    )
    public static final String JAIL_LIST_PERMISSION = "eternalcore.jail.list";

    @PermissionDocs(
        name = "Jail Setup",
        permission = JailPermissionConstant.JAIL_SETUP_PERMISSION,
        description = "Permission allows to setup jail area."
    )
    public static final String JAIL_SETUP_PERMISSION = "eternalcore.jail.setup";
}
