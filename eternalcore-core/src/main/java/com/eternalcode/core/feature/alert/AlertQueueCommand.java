package com.eternalcode.core.feature.alert;

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

@Command(name = "alert-queue", aliases = {"alert-q"})
@Permission("eternalcore.alert.queue")
class AlertQueueCommand {

    private final NoticeService noticeService;
    private final AlertManager alertService;

    @Inject
    AlertQueueCommand(
        NoticeService noticeService,
        AlertManager alertService
    ) {
        this.noticeService = noticeService;
        this.alertService = alertService;
    }

    @Execute(name = "add")
    @DescriptionDocs(description = "Adds alert to the queue with specified notice type and messages", arguments = "<type> <message>")
    void executeAdd(@Sender Player sender, @Arg NoticeTextType type, @Join String text) {
        this.alertService.addBroadcast(
            sender.getUniqueId(), type, this.noticeService.create()
                .notice(type, translation -> translation.chat().alertMessageFormat())
                .placeholder("{BROADCAST}", text)
                .onlinePlayers());

        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> translation.chat().alertQueueAdded())
            .send();
    }

    @Execute(name = "remove")
    @DescriptionDocs(description = "Removes all alerts of the given type from the queue", arguments = "<type> all")
    void executeRemoveAll(@Sender Player sender, @Arg NoticeTextType type, @Literal("all") String all) {
        boolean success = this.alertService.removeBroadcastWithType(sender.getUniqueId(), type);

        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> success
                ? translation.chat().alertQueueRemovedAll()
                : translation.chat().alertQueueEmpty())
            .send();
    }

    @Execute(name = "remove")
    @DescriptionDocs(description = "Removes a latest alert of the given type from the queue", arguments = "<type> latest")
    void executeRemove(@Sender Player sender, @Arg NoticeTextType type, @Literal("latest") String latest) {
        boolean success = this.alertService.removeLatestBroadcastWithType(sender.getUniqueId(), type);

        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> success
                ? translation.chat().alertQueueRemovedSingle()
                : translation.chat().alertQueueEmpty())
            .send();
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Clears all alerts from the queue")
    void executeClear(@Sender Player sender) {
        if (this.alertService.hasNoBroadcasts(sender.getUniqueId())) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.chat().alertQueueEmpty())
                .send();
            return;
        }

        this.alertService.clearBroadcasts(sender.getUniqueId());
        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> translation.chat().alertQueueCleared())
            .send();
    }

    @Execute(name = "send")
    @DescriptionDocs(description = "Sends all alerts from the queue")
    void executeSend(@Sender Player sender, @Arg Optional<Duration> duration) {
        UUID uniqueId = sender.getUniqueId();

        if (this.alertService.hasNoBroadcasts(sender.getUniqueId())) {
            this.noticeService.create()
                .player(uniqueId)
                .notice(translation -> translation.chat().alertQueueEmpty())
                .send();
            return;
        }

        this.alertService.send(uniqueId, duration.orElse(Duration.ofSeconds(2)));
        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> translation.chat().alertQueueSent())
            .send();
    }
}
