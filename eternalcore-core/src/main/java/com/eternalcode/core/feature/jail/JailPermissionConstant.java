package com.eternalcode.core.feature.jail;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

public class JailPermissionConstant {

    @PermissionDocs(
        name = "Jail Bypass",
        permission = JAIL_BYPASS_PERMISSION,
        description = "Permission allows to bypass jail punishment."
    )
    public static final String JAIL_BYPASS_PERMISSION = "eternalcore.jail.bypass";

    @PermissionDocs(
        name = "Jail Detain",
        permission = JAIL_DETAIN_PERMISSION,
        description = "Allows to detain players in jail."
    )
    public static final String JAIL_DETAIN_PERMISSION = "eternalcore.jail.detain";

    @PermissionDocs(
        name = "Jail Release",
        permission = JAIL_RELEASE_PERMISSION,
        description = "Allows to release players from jail."
    )
    public static final String JAIL_RELEASE_PERMISSION = "eternalcore.jail.release";

    @PermissionDocs(
        name = "Jail List",
        permission = JAIL_LIST_PERMISSION,
        description = "Allows to list all jailed players."
    )
    public static final String JAIL_LIST_PERMISSION = "eternalcore.jail.list";

    @PermissionDocs(
        name = "Jail Setup",
        permission = JAIL_SETUP_PERMISSION,
        description = "Permission allows to setup jail area."
    )
    public static final String JAIL_SETUP_PERMISSION = "eternalcore.jail.setup";
}
