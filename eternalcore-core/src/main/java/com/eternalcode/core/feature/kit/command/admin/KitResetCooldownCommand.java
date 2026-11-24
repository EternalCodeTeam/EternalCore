package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitUsageService;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin resetcooldown")
@Permission("eternalcore.kits.admin.resetcooldown")
class KitResetCooldownCommand {

    private final KitUsageService usageService;
    private final KitNotices notices;

    @Inject
    KitResetCooldownCommand(KitUsageService usageService, KitNotices notices) {
        this.usageService = usageService;
        this.notices = notices;
    }

    @Execute
    @DescriptionDocs(description = "Resets player's kit cooldown and one-time claim", arguments = "<player> <kit>")
    void reset(@Sender CommandSender sender, @Arg OfflinePlayer target, @Arg Kit kit) {
        String targetName = target.getName() == null ? target.getUniqueId().toString() : target.getName();

        this.usageService.resetUsage(target.getUniqueId(), kit);

        Formatter formatter = new Formatter()
            .register(KitNotices.KIT, kit.displayName())
            .register(KitNotices.PLAYER, targetName);

        this.notices.send(sender, KitMessages::usageReset, formatter);
    }
}
