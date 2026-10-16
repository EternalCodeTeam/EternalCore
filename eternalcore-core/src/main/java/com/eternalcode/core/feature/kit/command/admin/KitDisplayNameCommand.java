package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Map;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin displayname")
@Permission(KitPermissions.ADMIN)
class KitDisplayNameCommand {

    private final KitAdminSaver saver;

    @Inject
    KitDisplayNameCommand(KitAdminSaver saver) {
        this.saver = saver;
    }

    @Execute
    @DescriptionDocs(description = "Changes kit display name (MiniMessage)", arguments = "<kit> <display name>")
    void setDisplayName(@Sender CommandSender sender, @Arg Kit kit, @Join String displayName) {
        this.saver.save(sender, kit.toBuilder().displayName(displayName).build(), KitMessages::displayNameChanged, Map.of(
            KitNotices.NAME, displayName
        ));
    }
}
