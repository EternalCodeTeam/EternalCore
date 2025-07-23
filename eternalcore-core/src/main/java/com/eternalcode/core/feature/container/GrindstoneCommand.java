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
        PaperContainer.GRINDSTONE.open(player);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.grindstone.other")
    @DescriptionDocs(description = "Opens a grindstone for another player", arguments = "<player>")
    void execute(@Context CommandSender sender, @Arg Player target) {
        PaperContainer.GRINDSTONE.open(target);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .placeholder("{PLAYER}", target.getName())
            .sender(sender)
            .send();

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .player(target.getUniqueId())
            .placeholder("{PLAYER}", sender.getName())
            .send();
    }

}

