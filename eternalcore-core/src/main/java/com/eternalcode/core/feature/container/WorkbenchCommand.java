package com.eternalcode.core.feature.container;


import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
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
    void executeSelf(@Context Player sender) {
        sender.openWorkbench(null, true);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.workbench.other")
    @DescriptionDocs(description = "Opens a workbench for another player", arguments = "<player>")
    void execute(@Context Player sender, @Arg Player target) {
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

