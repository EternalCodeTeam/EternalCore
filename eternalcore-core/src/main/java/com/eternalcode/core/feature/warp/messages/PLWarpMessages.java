package com.eternalcode.core.feature.warp.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLWarpMessages extends OkaeriConfig implements WarpMessages {
    @Comment("# {WARP} - Nazwa warpu")
    Notice warpAlreadyExists = Notice.chat("<red>✘ <dark_red>Warp o nazwie <dark_red>{WARP} <red>już istnieje!");
    Notice create = Notice.chat("<color:#9d6eef>► <white>Stworzono warp <color:#9d6eef>{WARP}<white>!");
    Notice remove = Notice.chat("<color:#9d6eef>► <white>Usunięto warp <color:#9d6eef>{WARP}<white>!");
    Notice notExist = Notice.chat("<red>✘ <dark_red>Nie odnaleziono takiego warpu!");
    Notice itemAdded = Notice.chat("<color:#9d6eef>► <white>Dodano warp do GUI!");
    Notice noWarps = Notice.chat("<red>✘ <dark_red>Nie ma dostępnych warpów!");
    Notice itemLimit = Notice.chat("<red>✘ <dark_red>Osiągnąłeś limit warpów w GUI! Limit to: {LIMIT}!");
    Notice noPermission = Notice
            .chat("<red>✘ <red>Nie masz uprawnień do skorzystania z tego warpa <dark_red>{WARP}<red>!");
    Notice addPermissions = Notice
            .chat("<color:#9d6eef>► <white>Dodano uprawnienia do warpa <color:#9d6eef>{WARP}<white>!");
    Notice removePermission = Notice.chat(
            "<color:#9d6eef>► <white>Usunięto uprawnienie <color:#9d6eef>{PERMISSION}</color:#9d6eef> z warpa <color:#9d6eef>{WARP}</color:#9d6eef>!");
    Notice noPermissionsProvided = Notice.chat("<red>✘ <dark_red>Nie podano żadnych uprawnień!");
    Notice permissionDoesNotExist = Notice.chat("<red>✘ <dark_red>Podane uprawnienie nie istnieje ({PERMISSION})!");
    Notice permissionAlreadyExist = Notice.chat("<red>✘ <dark_red>Podane uprawnienie już istnieje ({PERMISSION})!");
    Notice noPermissionAssigned = Notice.chat("<red>✘ <dark_red>Ten warp nie ma przypisanych żadnych permisji");
    Notice missingWarpArgument = Notice.chat("<red>✘ <dark_red>Musisz podać nazwę warpu!");
    Notice missingPermissionArgument = Notice.chat("<red>✘ <dark_red>Musisz podać uprawnienie!");

    @Comment({" ", "# {WARPS} - Lista dostępnych warpów"})
    Notice available = Notice.chat("<color:#9d6eef>► <white>Dostepne warpy: <color:#9d6eef>{WARPS}!");
}
