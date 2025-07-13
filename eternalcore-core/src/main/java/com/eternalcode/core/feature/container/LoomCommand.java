package com.eternalcode.core.feature.container;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "loom")
class LoomCommand {

    private final NoticeService noticeService;

    @Inject
    LoomCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.loom")
    @DescriptionDocs(description = "Opens a loom for you")
    void executeSelf(@Context Player player) {
        AdditionalContainerPaper.LOOM.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.loom.other")
    @DescriptionDocs(description = "Opens a loom for another player", arguments = "<player>")
    void execute(@Context CommandSender sender, @Arg Player target) {
        AdditionalContainerPaper.LOOM.open(target);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .placeholder("{PLAYER}", sender.getName())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .sender(sender)
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}
