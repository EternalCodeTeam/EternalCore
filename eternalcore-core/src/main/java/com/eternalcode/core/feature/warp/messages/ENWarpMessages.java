package com.eternalcode.core.feature.warp.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENWarpMessages extends OkaeriConfig implements WarpMessages {
    @Comment("# {WARP} - Warp name")
    Notice warpAlreadyExists = Notice.chat("<red>✘ <dark_red>Warp <red>{WARP} <dark_red>already exists!");
    Notice create = Notice.chat("<color:#9d6eef>► <white>Warp <color:#9d6eef>{WARP} <white>has been created.");
    Notice remove = Notice.chat("<color:#9d6eef>► <white>Warp <color:#9d6eef>{WARP} <white>has been deleted.");
    Notice notExist = Notice.chat("<red>✘ <dark_red>This warp doesn't exist");
    Notice itemAdded = Notice.chat("<color:#9d6eef>► <white>Warp has been added to GUI!");
    Notice noWarps = Notice.chat("<red>✘ <dark_red>There are no warps!");
    Notice itemLimit = Notice
            .chat("<red>✘ <dark_red>You have reached the limit of warps! Your limit is <red>{LIMIT}<dark_red>.");
    Notice noPermission = Notice.chat("<red>✘ <dark_red>You don't have permission to use this warp ({WARP})!");
    Notice addPermissions = Notice
            .chat("<color:#9d6eef>► <white>Added permissions to warp <color:#9d6eef>{WARP}<white>!");
    Notice removePermission = Notice.chat(
            "<color:#9d6eef>► <white>Removed permission <color:#9d6eef>{PERMISSION}</color:#9d6eef> <white>from warp <color:#9d6eef>{WARP}<white>!");
    Notice noPermissionsProvided = Notice.chat("<red>✘ <dark_red>No permissions provided!");
    Notice permissionDoesNotExist = Notice
            .chat("<red>✘ <dark_red>Permission <red>{PERMISSION} <dark_red>doesn't exist!");
    Notice permissionAlreadyExist = Notice
            .chat("<red>✘ <dark_red>Permission <red>{PERMISSION} <dark_red>already exists!");
    Notice noPermissionAssigned = Notice.chat("<red>✘ <dark_red>There are no permissions assigned to this warp!");
    Notice missingWarpArgument = Notice.chat("<red>✘ <dark_red>You must provide a warp name!");
    Notice missingPermissionArgument = Notice.chat("<red>✘ <dark_red>You must provide a permission!");

    @Comment({ " ", "# {WARPS} - List of warps (separated by commas)" })
    Notice available = Notice.chat("<color:#9d6eef>► <white>Available warps: <color:#9d6eef>{WARPS}");
}
