package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.containers.AdditionalContainerType;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "grindstone")
@Permission("eternalcore.grindstone")
public class GrindstoneCommand {

    private final NoticeService announcer;

    public GrindstoneCommand(NoticeService announcer) {
        this.announcer = announcer;
    }

    @Execute(required = 0)
    void executeSelf(Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.GRINDSTONE);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    void execute(Player sender, @Arg Player target) {
        AdditionalContainerPaper.openAdditionalContainer(target, AdditionalContainerType.GRINDSTONE);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .placeholder("{PLAYER}", target.getName())
            .player(sender.getUniqueId())
            .send();

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .player(target.getUniqueId())
            .placeholder("{PLAYER}", sender.getName())
            .send();
    }

}

