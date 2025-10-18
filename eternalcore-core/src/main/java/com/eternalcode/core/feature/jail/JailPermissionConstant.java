package com.eternalcode.core.feature.jail;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

public class JailPermissionConstant {

    public static final String JAIL_COMMAND_PERMISSION = "eternalcore.jail";

    @PermissionDocs(
        name = "Jail Bypass",
        permission = JailPermissionConstant.JAIL_BYPASS_PERMISSION,
        description = "Permission allows to bypass jail punishment."
    )
    public static final String JAIL_BYPASS_PERMISSION = "eternalcore.jail.bypass";

    public static final String JAIL_DETAIN_PERMISSION = "eternalcore.jail.detain";
    public static final String JAIL_RELEASE_PERMISSION = "eternalcore.jail.release";
    public static final String JAIL_LIST_PERMISSION = "eternalcore.jail.list";
    public static final String JAIL_SETUP_PERMISSION = "eternalcore.jail.setup";
}
