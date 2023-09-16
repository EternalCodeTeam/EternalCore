package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "grindstone")
class GrindstoneCommand {

    private final NoticeService announcer;

    @Inject
    GrindstoneCommand(NoticeService announcer) {
        this.announcer = announcer;
    }

    @Execute(required = 0)
    @Permission("eternalcore.grindstone")
    @DescriptionDocs(description = "Opens a grindstone for you")
    void executeSelf(Player player) {
        AdditionalContainerPaper.GRINDSTONE.open(player);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    @Permission("eternalcore.grindstone.other")
    @DescriptionDocs(description = "Opens a grindstone for another player", arguments = "<player>")
    void execute(Player sender, @Arg Player target) {
        AdditionalContainerPaper.GRINDSTONE.open(target);

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

