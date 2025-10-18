package com.eternalcode.core.feature.ignore;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Command(name = "ignore")
@Permission("eternalcore.ignore")
class IgnoreCommand {

    private final IgnoreService ignoreService;
    private final NoticeService noticeService;

    @Inject
    IgnoreCommand(IgnoreService ignoreService, NoticeService noticeService) {
        this.ignoreService = ignoreService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Ignore specified player", arguments = "<player>")
    void ignore(@Context User sender, @Arg User target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        if (sender.equals(target)) {
            this.noticeService.viewer(sender, translation -> translation.ignore().cannotIgnoreSelf());
            return;
        }

        this.ignoreService.isIgnored(senderUuid, targetUuid)
            .thenCompose(isIgnored -> {
                if (isIgnored) {
                    this.noticeService.create()
                        .user(sender)
                        .placeholder("{PLAYER}", target.getName())
                        .notice(translation -> translation.ignore().playerAlreadyIgnored())
                        .send();
                    return CompletableFuture.completedFuture(IgnoreResult.CANCELLED);
                }

                return this.ignoreService.ignore(senderUuid, targetUuid);
            })
            .thenAccept(result -> {
                if (result == IgnoreResult.SUCCESS) {
                    this.noticeService.create()
                        .player(senderUuid)
                        .placeholder("{PLAYER}", target.getName())
                        .notice(translation -> translation.ignore().playerIgnored())
                        .send();
                }
            })
            .exceptionally(FutureHandler::handleException);
    }

    @Execute(name = "-all", aliases = "*")
    @DescriptionDocs(description = "Ignore all players")
    void ignoreAll(@Context User sender) {
        UUID senderUuid = sender.getUniqueId();

        this.ignoreService.ignoreAll(senderUuid)
            .thenAccept(result -> {
                if (result == IgnoreResult.SUCCESS) {
                    this.noticeService.create()
                        .player(senderUuid)
                        .notice(translation -> translation.ignore().allPlayersIgnored())
                        .send();
                }
            })
            .exceptionally(FutureHandler::handleException);
    }
}
