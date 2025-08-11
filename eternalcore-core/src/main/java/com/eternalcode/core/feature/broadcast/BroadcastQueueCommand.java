package com.eternalcode.core.feature.broadcast;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.literal.Literal;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;

@Command(name = "broadcast-queue", aliases = "broadcast-q")
@Permission("eternalcore.broadcast.queue")
class BroadcastQueueCommand {

    private final BroadcastManager broadcastManager;
    private final NoticeService noticeService;

    @Inject
    BroadcastQueueCommand(BroadcastManager broadcastManager, NoticeService noticeService) {
        this.broadcastManager = broadcastManager;
        this.noticeService = noticeService;
    }

    @Execute(name = "add")
    @DescriptionDocs(description = "Adds broadcast to the queue with specified notice type and messages", arguments = "<type> <message>")
    void executeAdd(@Sender Player sender, @Arg NoticeTextType type, @Join String text) {
        this.broadcastManager.addBroadcast(
            sender.getUniqueId(), type, this.noticeService.create()
                .notice(type, translation -> translation.broadcast().messageFormat())
                .placeholder("{BROADCAST}", text)
                .onlinePlayers());

        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> translation.broadcast().queueAdded())
            .send();
    }

    @Execute(name = "remove")
    @DescriptionDocs(description = "Removes all broadcast of the given type from the queue", arguments = "<type> all")
    void executeRemoveAll(@Sender Player sender, @Arg NoticeTextType type, @Literal("all") String all) {
        boolean success = this.broadcastManager.removeBroadcastWithType(sender.getUniqueId(), type);

        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> success
                ? translation.broadcast().queueRemovedAll()
                : translation.broadcast().queueEmpty())
            .send();
    }

    @Execute(name = "remove")
    @DescriptionDocs(description = "Removes a latest broadcast of the given type from the queue", arguments = "<type> latest")
    void executeRemove(@Sender Player sender, @Arg NoticeTextType type, @Literal("latest") String latest) {
        boolean success = this.broadcastManager.removeLatestBroadcastWithType(sender.getUniqueId(), type);

        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> success
                ? translation.broadcast().queueRemovedSingle()
                : translation.broadcast().queueEmpty())
            .send();
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Clears all broadcast from the queue")
    void executeClear(@Sender Player sender) {
        if (this.broadcastManager.hasNoBroadcasts(sender.getUniqueId())) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.broadcast().queueEmpty())
                .send();
            return;
        }

        this.broadcastManager.clearBroadcasts(sender.getUniqueId());
        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> translation.broadcast().queueCleared())
            .send();
    }

    @Execute(name = "send")
    @DescriptionDocs(description = "Sends all broadcast from the queue")
    void executeSend(@Sender Player sender, @Arg Optional<Duration> duration) {
        UUID uniqueId = sender.getUniqueId();

        if (this.broadcastManager.hasNoBroadcasts(sender.getUniqueId())) {
            this.noticeService.create()
                .player(uniqueId)
                .notice(translation -> translation.broadcast().queueEmpty())
                .send();
            return;
        }

        this.broadcastManager.send(uniqueId, duration.orElse(Duration.ofSeconds(2)));
        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> translation.broadcast().queueSent())
            .send();
    }
}
