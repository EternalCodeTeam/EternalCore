package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "workbench")
@Permission("eternalcore.workbench")
public class WorkbenchCommand {

    private final NoticeService announcer;

    public WorkbenchCommand(NoticeService announcer) {
        this.announcer = announcer;
    }

    @Execute
    void execute(Player invoker, @Arg Player target) {
        target.openWorkbench(null, true);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .player(target.getUniqueId())
            .placeholder("{PLAYER}", invoker.getName())
            .send();

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .player(invoker.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    @Execute
    void executeSelf(Player sender) {
        sender.openWorkbench(null, true);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(sender.getUniqueId())
            .send();
    }

}

