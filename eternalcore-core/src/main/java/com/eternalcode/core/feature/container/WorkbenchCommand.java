package com.eternalcode.core.feature.container;


import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "workbench")
class WorkbenchCommand {

    private final NoticeService noticeService;

    @Inject
    WorkbenchCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.workbench")
    @DescriptionDocs(description = "Opens a workbench for you")
    void executeSelf(@Context Player player) {
        this.openWorkbench(player);
    }

    @Execute
    @Permission("eternalcore.workbench.other")
    @DescriptionDocs(description = "Opens a workbench for another player", arguments = "<player>")
    void execute(@Context CommandSender commandSender, @Arg Player target) {
        this.openWorkbench(target);

        this.noticeService.create()
            .notice(translation -> translation.container().targetWorkbenchOpened())
            .sender(commandSender)
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    void openWorkbench(Player player) {
        player.openWorkbench(null, true);

        this.noticeService.create()
            .notice(translation -> translation.container().workbenchOpened())
            .player(player.getUniqueId())
            .send();
    }

}

