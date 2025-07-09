package com.eternalcode.core.feature.vanish;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

@PermissionDocs(
    name = "Vanish tabulation",
    permission = VanishPermissionConstant.VANISH_SEE_TABULATION_PERMISSION,
    description = "Allows the player to see vanished players in tabulation."
)
public class VanishPermissionConstant {

    public static final String VANISH_SEE_TABULATION_PERMISSION = "eternalcore.vanish.tabulation.see";
}
