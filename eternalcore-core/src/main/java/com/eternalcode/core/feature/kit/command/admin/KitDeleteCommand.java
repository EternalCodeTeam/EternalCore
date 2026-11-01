package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Map;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin delete")
@Permission(KitPermissions.ADMIN)
class KitDeleteCommand {

    private final KitService kitService;
    private final KitNotices notices;

    @Inject
    KitDeleteCommand(KitService kitService, KitNotices notices) {
        this.kitService = kitService;
        this.notices = notices;
    }

    @Execute
    @DescriptionDocs(description = "Deletes the kit file and all its cooldowns", arguments = "<kit>")
    void delete(@Sender CommandSender sender, @Arg Kit kit) {
        this.kitService.deleteKit(kit.name());
        this.notices.send(sender, KitMessages::deleted, Map.of(KitNotices.KIT, kit.name()));
    }
}
