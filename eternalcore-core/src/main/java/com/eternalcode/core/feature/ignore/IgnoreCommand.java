package com.eternalcode.core.feature.ignore;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Server;

import java.util.UUID;

// TODO: Add translations when event is cancelled

@Command(name = "ignore")
@Permission("eternalcore.ignore")
class IgnoreCommand {

    private final IgnoreService ignoreService;
    private final NoticeService noticeService;
    private final EventCaller eventCaller;
    private final Server server;

    @Inject
    IgnoreCommand(IgnoreService ignoreService, NoticeService noticeService, EventCaller eventCaller, Server server) {
        this.ignoreService = ignoreService;
        this.noticeService = noticeService;
        this.eventCaller = eventCaller;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Ignore specified player", arguments = "<player>")
    void ignore(@Context User sender, @Arg User target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        if (sender.equals(target)) {
            this.noticeService.viewer(sender, translation -> translation.privateChat().cantIgnoreYourself());
            return;
        }

        this.ignoreService.isIgnored(senderUuid, targetUuid).thenAccept(isIgnored -> {
            if (isIgnored) {
                this.noticeService.create()
                    .user(sender)
                    .placeholder("{PLAYER}", target.getName())
                    .notice(translation -> translation.privateChat().alreadyIgnorePlayer())
                    .send();

                return;
            }

            this.ignoreService.ignore(senderUuid, targetUuid).thenAccept(cancelled -> {
                if (cancelled) {
                    return;
                }

                this.noticeService.create()
                    .player(senderUuid)
                    .placeholder("{PLAYER}", target.getName())
                    .notice(translation -> translation.privateChat().ignorePlayer())
                    .send();
            });
        });
    }

    @Execute(name = "-all", aliases = "*")
    @DescriptionDocs(description = "Ignore all players")
    void ignoreAll(@Context User sender) {
        UUID senderUuid = sender.getUniqueId();

        this.ignoreService.ignoreAll(senderUuid).thenAccept(cancelled -> {
            if (cancelled) {
                return;
            }

            this.noticeService.create()
                .player(senderUuid)
                .notice(translation -> translation.privateChat().ignoreAll())
                .send();
        });
    }

}
