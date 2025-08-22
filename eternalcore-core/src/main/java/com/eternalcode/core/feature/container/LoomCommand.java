package com.eternalcode.core.feature.container;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.paper.PaperContainer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
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
    void executeSelf(@Sender Player player) {
        this.openLoom(player);
    }

    @Execute
    @Permission("eternalcore.loom.other")
    @DescriptionDocs(description = "Opens a loom for another player", arguments = "<player>")
    void execute(@Sender CommandSender commandSender, @Arg Player target) {
        this.openLoom(target);

        this.noticeService.create()
            .notice(translation -> translation.container().targetLoomOpened())
            .sender(commandSender)
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    void openLoom(Player player) {
        PaperContainer.LOOM.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().loomOpened())
            .player(player.getUniqueId())
            .send();
    }

}
