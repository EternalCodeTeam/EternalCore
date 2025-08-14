package com.eternalcode.core.feature.near;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

public class NearPermissionConstant {

    @PermissionDocs(
        name = "Near",
        permission = NearPermissionConstant.NEAR_PERMISSION,
        description = "Allows the player to list the surrounding entities."
    )
    public static final String NEAR_PERMISSION = "eternalcore.near";

    @PermissionDocs(
        name = "Near Glow",
        permission = NearPermissionConstant.NEAR_GLOW_PERMISSION,
        description = "Allows the player to highlight the surrounding entities with a glow effect. "
    )
    public static final String NEAR_GLOW_PERMISSION = "eternalcore.near.glow";

}
