package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitCooldownService;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Map;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin resetcooldown")
@Permission(KitPermissions.ADMIN)
class KitResetCooldownCommand {

    private final KitCooldownService cooldownService;
    private final KitNotices notices;

    @Inject
    KitResetCooldownCommand(KitCooldownService cooldownService, KitNotices notices) {
        this.cooldownService = cooldownService;
        this.notices = notices;
    }

    @Execute
    @DescriptionDocs(description = "Resets player's kit cooldown", arguments = "<player> <kit>")
    void reset(@Sender CommandSender sender, @Arg OfflinePlayer target, @Arg Kit kit) {
        String targetName = target.getName() == null ? target.getUniqueId().toString() : target.getName();

        this.cooldownService.resetCooldown(target.getUniqueId(), kit);

        this.notices.send(sender, KitMessages::cooldownReset, Map.of(
            KitNotices.KIT, kit.name(),
            KitNotices.PLAYER, targetName
        ));
    }
}
