package com.eternalcode.core.feature.container;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.paper.PaperContainer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
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
    void executeSelf(@Sender Player player) {
        this.openGrindstone(player);
    }

    @Execute
    @Permission("eternalcore.grindstone.other")
    @DescriptionDocs(description = "Opens a grindstone for another player", arguments = "<player>")
    void execute(@Sender CommandSender commandSender, @Arg Player target) {
        this.openGrindstone(target);

        this.announcer.create()
            .notice(translation -> translation.container().targetGrindstoneOpened())
            .sender(commandSender)
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    void openGrindstone(Player target) {
        PaperContainer.GRINDSTONE.open(target);

        this.announcer.create()
            .notice(translation -> translation.container().grindstoneOpened())
            .player(target.getUniqueId())
            .send();
    }

}

