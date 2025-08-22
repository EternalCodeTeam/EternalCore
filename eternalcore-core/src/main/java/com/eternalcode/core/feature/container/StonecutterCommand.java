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

@Command(name = "stonecutter")
class StonecutterCommand {

    private final NoticeService noticeService;

    @Inject
    StonecutterCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.stonecutter")
    @DescriptionDocs(description = "Opens a stonecutter for you")
    void executeSelf(@Sender Player player) {
        this.openStonecutter(player);
    }

    @Execute
    @Permission("eternalcore.stonecutter.other")
    @DescriptionDocs(description = "Opens a stonecutter for another player", arguments = "<player>")
    void execute(@Sender CommandSender commandSender, @Arg Player target) {
        this.openStonecutter(target);

        this.noticeService.create()
            .notice(translation -> translation.container().targetStonecutterOpened())
            .sender(commandSender)
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    void openStonecutter(Player player) {
        PaperContainer.STONE_CUTTER.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().stonecutterOpened())
            .player(player.getUniqueId())
            .send();
    }

}

