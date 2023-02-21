package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.containers.AdditionalContainerType;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "stonecutter")
@Permission("eternalcore.workbench")
public class StonecutterCommand {

    private final NoticeService announcer;

    public StonecutterCommand(NoticeService announcer) {
        this.announcer = announcer;
    }

    @Execute
    void executeSelf(Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.STONE_CUTTER);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    void execute(Player invoker, @Arg Player target) {
        AdditionalContainerPaper.openAdditionalContainer(target, AdditionalContainerType.STONE_CUTTER);

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

}

