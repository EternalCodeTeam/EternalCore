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
    public Notice warpAlreadyExists = Notice.chat("<red>✘ <dark_red>Warp o nazwie <dark_red>{WARP} <red>już istnieje!");
    public Notice create = Notice.chat("<green>► <white>Stworzono warp <green>{WARP}<white>!");
    public Notice remove = Notice.chat("<red>► <white>Usunięto warp <red>{WARP}<white>!");
    public Notice notExist = Notice.chat("<red>► <dark_red>Nie odnaleziono takiego warpu!");
    public Notice itemAdded = Notice.chat("<green>► <white>Dodano warp do GUI!");
    public Notice noWarps = Notice.chat("<red>✘ <dark_red>Nie ma dostępnych warpów!");
    public Notice itemLimit = Notice.chat("<red>✘ <red>Osiągnąłeś limit warpów w GUI! Limit to: {LIMIT}!");
    public Notice noPermission =
        Notice.chat("<red>✘ <red>Nie masz uprawnień do skorzystania z tego warpa <dark_red>{WARP}<red>!");
    public Notice addPermissions = Notice.chat("<green>► <white>Dodano uprawnienia do warpa <green>{WARP}<white>!");
    public Notice removePermission =
        Notice.chat("<red>► <white>Usunięto uprawnienie <red>{PERMISSION}</red> z warpa <red>{WARP}</red>!");
    public Notice noPermissionsProvided = Notice.chat("<red>✘ <red>Nie podano żadnych uprawnień!");
    public Notice permissionDoesNotExist = Notice.chat("<red>✘ <red>Podane uprawnienie nie istnieje ({PERMISSION})!");
    public Notice permissionAlreadyExist = Notice.chat("<red>✘ <red>Podane uprawnienie już istnieje ({PERMISSION})!");
    public Notice noPermissionAssigned = Notice.chat("<red>✘ <red>Ten warp nie ma przypisanych żadnych permisji");
    public Notice missingWarpArgument = Notice.chat("<red>✘ <dark_red>Musisz podać nazwę warpu!");
    public Notice missingPermissionArgument = Notice.chat("<red>✘ <dark_red>Musisz podać uprawnienie!");

    @Comment({" ", "# {WARPS} - Lista dostępnych warpów"})
    public Notice available = Notice.chat("<green>► <white>Dostepne warpy: <green>{WARPS}!");
}
