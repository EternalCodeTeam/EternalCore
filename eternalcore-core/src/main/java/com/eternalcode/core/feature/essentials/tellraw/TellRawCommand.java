package com.eternalcode.core.feature.essentials.tellraw;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import java.util.UUID;
import org.bukkit.entity.Player;

@Command(name = "tellraw")
@Permission({ "eternalcore.tellraw" })
class TellRawCommand {

    private final NoticeService noticeService;
    private final TellRawService tellRawService;

    @Inject
    TellRawCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
        this.tellRawService = new TellRawService();
    }

    @Execute(name = "send")
    @DescriptionDocs(description = "Send a message to the player without any prefixes etc.", arguments = "<player> <notice_type> <message>")
    void tellRaw(@Context Viewer sender, @Arg Player target, @Arg NoticeTextType type, @Join String message) {
        this.noticeService.create()
            .notice(type, message)
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.chat().tellrawInfo())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{MESSAGE}", message)
            .placeholder("{TYPE}", type.name())
            .viewer(sender)
            .send();
    }

    @Execute(name = "-all", aliases = "*")
    @DescriptionDocs(description = "Broadcast a message without any prefixes etc.", arguments = "<notice_type> <message>")
    void tellRawAll(@Context Viewer sender, @Arg NoticeTextType type, @Join String message) {
        this.noticeService.create()
            .notice(type, message)
            .onlinePlayers()
            .send();

        this.noticeService.create()
            .notice(translation -> translation.chat().tellrawAllInfo())
            .placeholder("{MESSAGE}", message)
            .placeholder("{TYPE}", type.name())
            .viewer(sender)
            .send();
    }

    @Execute(name = "add")
    @DescriptionDocs(description = "Save a message to send it later (in batch).", arguments = "<notice_type> <message>")
    void tellRawMultiple(@Context Viewer sender, @Arg NoticeTextType type, @Join String message) {

        this.tellRawService.addNotice(sender.getUniqueId(), new TellRawNotice(type, message));

        this.noticeService.create()
            .notice(translation -> translation.chat().tellrawSaved())
            .placeholder("{MESSAGE}", message)
            .placeholder("{TYPE}", type.name())
            .viewer(sender)
            .send();
    }

    @Execute(name = "send -all", aliases = "send *")
    @DescriptionDocs(description = "Send all saved messages to all players")
    void tellRawSend(@Context Viewer sender) {
        UUID uuid = sender.getUniqueId();

        if (!this.tellRawService.hasNotices(uuid)) {
            this.noticeService.create()
                .notice(translation -> translation.chat().tellrawNoSaved())
                .viewer(sender)
                .send();
            return;
        }

        for (TellRawNotice notice : this.tellRawService.getNotices(sender.getUniqueId())) {
            this.noticeService.create()
                .notice(notice.type(), notice.message())
                .onlinePlayers()
                .send();
        }

        this.tellRawService.removeNotices(uuid);

        this.noticeService.create()
            .notice(translation -> translation.chat().tellrawMultipleSent())
            .viewer(sender)
            .send();
    }

    @Execute(name = "send")
    @DescriptionDocs(description = "Send all saved messages to the player", arguments = "<player>")
    void tellRawSend(@Context Viewer sender, @Arg Player target) {
        UUID uuid = sender.getUniqueId();

        if (!this.tellRawService.hasNotices(uuid)) {
            this.noticeService.create()
                .notice(translation -> translation.chat().tellrawNoSaved())
                .placeholder("{PLAYER}", target.getName())
                .viewer(sender)
                .send();
            return;
        }

        for (TellRawNotice tellRawNotice : this.tellRawService.getNotices(uuid)) {
            this.noticeService.create()
                .notice(tellRawNotice.type(), tellRawNotice.message())
                .player(target.getUniqueId())
                .send();
        }

        this.tellRawService.removeNotices(uuid);

        this.noticeService.create()
            .notice(translation -> translation.chat().tellrawMultipleSent())
            .viewer(sender)
            .send();
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Clear all saved messages")
    void tellRawClear(@Context Viewer sender) {
        this.tellRawService.removeNotices(sender.getUniqueId());

        this.noticeService.create()
            .notice(translation -> translation.chat().tellrawCleared())
            .viewer(sender)
            .send();
    }

}
