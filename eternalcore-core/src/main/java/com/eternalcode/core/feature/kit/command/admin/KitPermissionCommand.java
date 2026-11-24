package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin permission")
@Permission("eternalcore.kits.admin.permission")
class KitPermissionCommand {

    private final KitAdminSaver saver;

    @Inject
    KitPermissionCommand(KitAdminSaver saver) {
        this.saver = saver;
    }

    @Execute
    @DescriptionDocs(description = "Changes permission required to claim the kit", arguments = "<kit> <permission>")
    void setPermission(@Sender CommandSender sender, @Arg Kit kit, @Arg String permission) {
        Kit updated = kit.toBuilder().permission(permission).build();

        this.saver.save(sender, updated, KitMessages::permissionChanged, new Formatter().register(KitNotices.PERMISSION, permission));
    }
}
