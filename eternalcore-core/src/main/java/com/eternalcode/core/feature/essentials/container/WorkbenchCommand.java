package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "workbench")
class WorkbenchCommand {

    private final NoticeService noticeService;

    @Inject
    WorkbenchCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    @Permission("eternalcore.workbench")
    @DescriptionDocs(description = "Opens a workbench for you")
    void executeSelf(Player sender) {
        sender.openWorkbench(null, true);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    @Permission("eternalcore.workbench.other")
    @DescriptionDocs(description = "Opens a workbench for another player", arguments = "<player>")
    void execute(Player sender, @Arg Player target) {
        target.openWorkbench(null, true);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .player(target.getUniqueId())
            .placeholder("{PLAYER}", sender.getName())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}

