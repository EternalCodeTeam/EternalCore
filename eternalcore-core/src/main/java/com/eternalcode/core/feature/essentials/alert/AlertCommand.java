package com.eternalcode.core.feature.essentials.alert;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;

@Command(name = "alert", aliases = { "broadcast", "bc" })
@Permission("eternalcore.alert")
class AlertCommand {

    private final NoticeService noticeService;
    private final AlertManager alertService;

    @Inject
    AlertCommand(
        NoticeService noticeService,
        AlertManager alertService
    ) {
        this.noticeService = noticeService;
        this.alertService = alertService;
    }

    @Execute
    @DescriptionDocs(description = "Sends alert to all players with specified notice type and messages", arguments = "<type> <message>")
    void execute(@Arg NoticeTextType type, @Join String text) {
        this.noticeService.create()
            .notice(type, translation -> translation.chat().alertMessageFormat())
            .placeholder("{BROADCAST}", text)
            .onlinePlayers()
            .send();
    }

    @Execute(name = "add")
    @DescriptionDocs(description = "Adds alert to the queue with specified notice type and messages", arguments = "<type> <message>")
    void executeAdd(@Arg NoticeTextType type, @Join String text, @Context Player player) {
        this.alertService.addBroadcast(type, this.noticeService.create()
            .notice(type, translation -> translation.chat().alertMessageFormat())
            .placeholder("{BROADCAST}", text)
            .onlinePlayers());
        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.chat().alertQueueAdded())
            .send();
    }

    @Execute(name = "remove")
    @DescriptionDocs(description = "Removes an alert from the queue with the specified notice type. Use the parameter all to remove all alerts of the given type, or latest to remove only the most recently added alert.", arguments = "<type> <param>")
    void executeRemove(@Arg NoticeTextType type, @Arg RemoveParam param, @Context Player player) {
        boolean success = false;
        switch (param) {
            case all:
                success = this.alertService.removeBroadcastWithType(type);
                break;
            case latest:
                success = this.alertService.removeLatestBroadcastWithType(type);
                break;
        }
        if (success) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.chat().alertQueueRemoved())
                .send();
        } else {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.chat().alertQueueEmpty())
                .send();
        }
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Clears all alerts from the queue")
    void executeClear(@Context Player player) {
        this.alertService.clearBroadcasts();
        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.chat().alertQueueCleared())
            .send();
    }

    @Execute(name = "send")
    @DescriptionDocs(description = "Sends all alerts from the queue")
    void executeSend(@Context Player player) {
        this.alertService.send();
        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.chat().alertQueueSent())
            .send();
    }
}
