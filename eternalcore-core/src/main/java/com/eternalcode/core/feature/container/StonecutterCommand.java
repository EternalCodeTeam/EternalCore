package com.eternalcode.core.feature.container;


import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.paper.PaperContainer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
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
    void executeSelf(@Context Player player) {
        PaperContainer.STONE_CUTTER.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.stonecutter.other")
    @DescriptionDocs(description = "Opens a stonecutter for another player", arguments = "<player>")
    void execute(@Context CommandSender sender, @Arg Player target) {
        PaperContainer.STONE_CUTTER.open(target);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .player(target.getUniqueId())
            .placeholder("{PLAYER}", sender.getName())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .sender(sender)
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}

