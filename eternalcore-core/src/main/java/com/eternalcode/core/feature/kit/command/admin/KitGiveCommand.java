package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimNotifier;
import com.eternalcode.core.feature.kit.KitClaimResult;
import com.eternalcode.core.feature.kit.KitClaimService;
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
import org.bukkit.entity.Player;

@Command(name = "kit give")
@Permission("eternalcore.kits.admin.give")
class KitGiveCommand {

    private final KitClaimService claimService;
    private final KitClaimNotifier claimNotifier;
    private final KitNotices notices;
    private final MinecraftScheduler scheduler;

    @Inject
    KitGiveCommand(KitClaimService claimService, KitClaimNotifier claimNotifier, KitNotices notices, MinecraftScheduler scheduler) {
        this.claimService = claimService;
        this.claimNotifier = claimNotifier;
        this.notices = notices;
        this.scheduler = scheduler;
    }

    @Execute
    @DescriptionDocs(description = "Gives the kit to the player, ignoring permission, cooldown and requirements", arguments = "<player> <kit>")
    void give(@Sender CommandSender sender, @Arg Player target, @Arg Kit kit) {
        this.scheduler.run(target, () -> {
            KitClaimResult result = this.claimService.giveKit(target, kit);

            if (!result.isSuccess()) {
                this.claimNotifier.notify(sender, kit, result);
                return;
            }

            Formatter formatter = new Formatter()
                .register(KitNotices.KIT, kit.displayName())
                .register(KitNotices.PLAYER, target.getName());

            this.notices.send(sender, KitMessages::given, formatter);

            if (result.bufferedItems() > 0) {
                this.claimNotifier.notify(target, kit, result);
                return;
            }

            this.notices.send(target, KitMessages::received, new Formatter().register(KitNotices.KIT, kit.displayName()));
        });
    }
}
