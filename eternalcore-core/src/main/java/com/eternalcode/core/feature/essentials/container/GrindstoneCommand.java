package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;

@Command(name = "grindstone")
class GrindstoneCommand {

    private final NoticeService announcer;

    @Inject
    GrindstoneCommand(NoticeService announcer) {
        this.announcer = announcer;
    }

    @Execute
    @Permission("eternalcore.grindstone")
    @DescriptionDocs(description = "Opens a grindstone for you")
    void executeSelf(@Context Player player) {
        AdditionalContainerPaper.GRINDSTONE.open(player);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.grindstone.other")
    @DescriptionDocs(description = "Opens a grindstone for another player", arguments = "<player>")
    void execute(@Context Player sender, @Arg Player target) {
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

