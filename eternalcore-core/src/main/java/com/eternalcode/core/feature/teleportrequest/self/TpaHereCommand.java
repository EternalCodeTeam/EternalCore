package com.eternalcode.core.feature.teleportrequest.self;

import com.eternalcode.core.feature.ignore.IgnoreService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

@Command(name = "tpahere")
@Permission("eternalcore.tpahere")
class TpaHereCommand {

    private final TeleportHereRequestService requestService;
    private final IgnoreService ignoreService;
    private final NoticeService noticeService;

    @Inject
    TpaHereCommand(TeleportHereRequestService requestService, IgnoreService ignoreService, NoticeService noticeService) {
        this.requestService = requestService;
        this.ignoreService = ignoreService;
        this.noticeService = noticeService;
    }

    @Execute
    void execute(@Context Player sender, @Arg Player target) {
        if (sender.equals(target)) {
            this.noticeService.player(sender.getUniqueId() , translation -> translation.tpa().tpaSelfMessage());

            return;
        }

        if (this.requestService.hasRequest(sender.getUniqueId(), target.getUniqueId())) {
            this.noticeService.player(sender.getUniqueId(), translation -> translation.tpa().tpaAlreadySentMessage());

            return;
        }

        this.isIgnoring(target, sender).thenAccept(isIgnoring -> {
           if (isIgnoring) {
               this.noticeService.create()
                   .player(sender.getUniqueId())
                   .notice(translation -> translation.tpa().tpaTargetIgnoresYou())
                   .placeholder("{PLAYER}", target.getName())
                   .send();
               return;
           }

           this.noticeService
               .create()
               .player(sender.getUniqueId())
               .notice(translation -> translation.tpa().tpaHereSent())
               .placeholder("{PLAYER}", target.getName())
               .send();

           this.noticeService.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.tpa().tpaHereReceived())
                .placeholder("{PLAYER}", sender.getName())
                .send();

           this.requestService.createRequest(sender.getUniqueId(), target.getUniqueId());
        });
    }

    private CompletableFuture<Boolean> isIgnoring(Player target, Player sender) {
        return this.ignoreService.isIgnored(target.getUniqueId(), sender.getUniqueId());
    }
}
