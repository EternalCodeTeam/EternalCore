package com.eternalcode.core.feature.enderchest;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Command(name = "enderchest", aliases = { "ec" })
class EnderchestCommand {

    private final EnderchestService enderchestService;
    private final NoticeService noticeService;

    @Inject
    EnderchestCommand(EnderchestService enderchestService, NoticeService noticeService) {
        this.enderchestService = enderchestService;
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.enderchest")
    @DescriptionDocs(description = "Opens your ender chest")
    void execute(@Sender Player player) {
        if (this.enderchestService.areEnderchestsBlocked()) {
            this.sendBlockedNotice(player);
            return;
        }

        this.enderchestService.openEnderchest(player);

        if (this.enderchestService.isVanillaEnderchestReplaced()) {
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.enderchest().openedEnderchest())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.enderchest")
    @DescriptionDocs(description = "Opens selected page of your ender chest", arguments = "<page>")
    void executePage(@Sender Player player, @Arg int page) {
        if (this.enderchestService.areEnderchestsBlocked()) {
            this.sendBlockedNotice(player);
            return;
        }

        if (!this.enderchestService.isVanillaEnderchestReplaced()) {
            this.sendCustomEnderchestDisabledNotice(player);
            return;
        }

        this.enderchestService.openEnderchest(player, player, page);
    }

    @Execute
    @Permission("eternalcore.enderchest.other")
    @DescriptionDocs(description = "Opens the ender chest of selected player (also offline)", arguments = "<player> [page]")
    void executeOther(@Sender Player viewer, @Arg OfflinePlayer target, @OptionalArg Integer page) {
        if (this.enderchestService.areEnderchestsBlocked()) {
            this.sendBlockedNotice(viewer);
            return;
        }

        if (!this.enderchestService.isVanillaEnderchestReplaced()) {
            this.sendCustomEnderchestDisabledNotice(viewer);
            return;
        }

        this.enderchestService.openEnderchest(viewer, target, page == null ? EnderchestLayout.FIRST_PAGE : page);
    }

    private void sendBlockedNotice(Player player) {
        this.noticeService.create()
            .notice(translation -> translation.enderchest().enderchestsBlocked())
            .player(player.getUniqueId())
            .send();
    }

    private void sendCustomEnderchestDisabledNotice(Player player) {
        this.noticeService.create()
            .notice(translation -> translation.enderchest().customEnderchestDisabled())
            .player(player.getUniqueId())
            .send();
    }
}
