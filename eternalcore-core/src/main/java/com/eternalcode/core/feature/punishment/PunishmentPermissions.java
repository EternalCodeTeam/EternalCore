package com.eternalcode.core.feature.punishment;

import com.eternalcode.annotations.scan.permission.PermissionDocs;

public class PunishmentPermissions {

    @PermissionDocs(
        name = "Punishment Staff Messages",
        permission = PunishmentPermissions.STAFF_MESSAGES,
        description = "Permission allows to see silent punishment broadcasts (e.g. /ban -s)"
    )
    public static final String STAFF_MESSAGES = "eternalcore.punishment.messages";

    @PermissionDocs(
        name = "History Self",
        permission = PunishmentPermissions.HISTORY_SELF,
        description = "Permission allows a player to view their own punishment history"
    )
    public static final String HISTORY_SELF = "eternalcore.punishment.history-self";
    public static final String HISTORY_STAFF = "eternalcore.punishment.history";

    public static final String BAN_BYPASS = "eternalcore.ban.bypass";
    public static final String BAN_IP_BYPASS = "eternalcore.banip.bypass";

    public static final String KICK_BYPASS = "eternalcore.kick.bypass";
    public static final String KICKALL_BYPASS = "eternalcore.kickall.bypass";

    public static final String MUTE_BYPASS = "eternalcore.mute.bypass";
    public static final String WARN_BYPASS = "eternalcore.warn.bypass";

}
