package com.eternalcode.core.feature.teleport.request;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.ignore.IgnoreService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;
import panda.std.reactive.Completable;

@Route(name = "tpa")
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

    @Execute(required = 1)
    @DescriptionDocs(description = "Send teleport request to player", arguments = "<player>")
    void execute(Player player, @Arg Player target) {
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

        this.isIgnoring(target, player).then((isIgnoring) -> {
            if (isIgnoring) {
                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.tpa().tpaTargetIgnoresYou())
                    .placeholder("{PLAYER}", target.getName())
                    .send();

                return;
            }

            this.noticeService
                .create()
                .player(target.getUniqueId())
                .notice(translation -> translation.tpa().tpaReceivedMessage())
                .placeholder("{PLAYER}", player.getName())
                .send();

            this.requestService.createRequest(player.getUniqueId(), target.getUniqueId());
        });
    }

    Completable<Boolean> isIgnoring(Player target, Player sender) {
        return this.ignoreService.isIgnored(target.getUniqueId(), sender.getUniqueId());
    }
}

