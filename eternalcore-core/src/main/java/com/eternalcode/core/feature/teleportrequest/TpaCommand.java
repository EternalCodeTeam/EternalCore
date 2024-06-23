package com.eternalcode.core.feature.teleportrequest;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.ignore.IgnoreService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import java.util.concurrent.CompletableFuture;
import org.bukkit.entity.Player;

@Command(name = "tpa")
@Permission("eternalcore.tpa")
class TpaCommand {

    private final TeleportRequestService requestService;
    private final IgnoreService ignoreService;
    private final NoticeService noticeService;

    @Inject
    TpaCommand(TeleportRequestService requestService, IgnoreService ignoreService, NoticeService noticeService) {
        this.requestService = requestService;
        this.ignoreService = ignoreService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Send teleport request to player", arguments = "<player>")
    void execute(@Context Player player, @Arg Player target) {
        if (player.equals(target)) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaSelfMessage());

            return;
        }

        if (this.requestService.hasRequest(player.getUniqueId(), target.getUniqueId())) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaAlreadySentMessage());

            return;
        }

        this.noticeService
            .create()
            .player(player.getUniqueId())
            .notice(translation -> translation.tpa().tpaSentMessage())
            .placeholder("{PLAYER}", target.getName())
            .send();

        this.isIgnoring(target, player).thenAccept((isIgnoring) -> {
            if (isIgnoring) {
                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.tpa().tpaTargetIgnoresYou())
                    .placeholder("{PLAYER}", target.getName())
                    .send();

                return;
            }

            this.noticeService.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.tpa().tpaReceivedMessage())
                .placeholder("{PLAYER}", player.getName())
                .send();

            this.requestService.createRequest(player.getUniqueId(), target.getUniqueId());
        });
    }

    CompletableFuture<Boolean> isIgnoring(Player target, Player sender) {
        return this.ignoreService.isIgnored(target.getUniqueId(), sender.getUniqueId());
    }
}

