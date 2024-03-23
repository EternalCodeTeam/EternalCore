package com.eternalcode.core.feature.ignore;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.ignore.event.UnIgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;

@Command(name = "unignore")
@Permission("eternalcore.ignore")
class UnIgnoreCommand {

    private final IgnoreService repository;
    private final NoticeService noticeService;
    private final EventCaller eventCaller;
    private final Server server;

    @Inject
    public UnIgnoreCommand(IgnoreService ignoreService, NoticeService noticeService, EventCaller eventCaller, Server server) {
        this.repository = ignoreService;
        this.noticeService = noticeService;
        this.eventCaller = eventCaller;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Unignore specified player", arguments = "<player>")
    void ignore(@Context User sender, @Arg User target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        if (sender.equals(target)) {
            this.noticeService.viewer(sender, translation -> translation.privateChat().cantUnIgnoreYourself());
            return;
        }

        this.repository.isIgnored(senderUuid, targetUuid).thenAccept(isIgnored -> {
            if (!isIgnored) {
                this.noticeService.create()
                    .user(sender)
                    .placeholder("{PLAYER}", target.getName())
                    .notice(translation -> translation.privateChat().notIgnorePlayer())
                    .send();

                return;
            }

            Player senderPlayer = this.server.getPlayer(senderUuid);
            Player targetPlayer = this.server.getPlayer(targetUuid);

            UnIgnoreEvent event = this.eventCaller.callEvent(new UnIgnoreEvent(senderPlayer, targetPlayer));

            if (event.isCancelled()) {
                return;
            }

            this.repository.unIgnore(senderUuid, targetUuid).thenAccept(blank -> this.noticeService.create()
                .player(senderUuid)
                .placeholder("{PLAYER}", target.getName())
                .notice(translation -> translation.privateChat().unIgnorePlayer())
                .send());
        });
    }

    @Execute(name = "-all", aliases = "*")
    @DescriptionDocs(description = "Unignore all players")
    void unIgnoreAll(@Context User sender) {
        UUID senderUuid = sender.getUniqueId();

        Player player = this.server.getPlayer(senderUuid);

        UnIgnoreAllEvent event = this.eventCaller.callEvent(new UnIgnoreAllEvent(player));

        if (event.isCancelled()) {
            return;
        }

        this.repository.unIgnoreAll(senderUuid).thenAccept(blank -> this.noticeService.create()
            .player(senderUuid)
            .notice(translation -> translation.privateChat().unIgnoreAll())
            .send());
    }

}
