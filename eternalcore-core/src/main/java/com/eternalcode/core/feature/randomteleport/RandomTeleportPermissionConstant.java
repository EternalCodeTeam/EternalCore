package com.eternalcode.core.feature.randomteleport;

import static com.eternalcode.core.feature.randomteleport.RandomTeleportPermissionConstant.RTP_BYPASS_PERMISSION;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

@PermissionDocs(
    name = "Random Teleport",
    permission = RTP_BYPASS_PERMISSION,
    description = "Allows you to bypass random teleport delay."
)
final class RandomTeleportPermissionConstant {

    static final String RTP_BYPASS_PERMISSION = "eternalcore.rtp.bypass";

    static final String RTP_COMMAND_SELF = "eternalcore.rtp";
    static final String RTP_COMMAND_OTHER = "eternalcore.rtp.other";
}
