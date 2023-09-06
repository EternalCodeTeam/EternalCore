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

@Route(name = "loom")
public class LoomCommand {

    private final NoticeService noticeService;

    @Inject
    public LoomCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    @Permission("eternalcore.loom")
    @DescriptionDocs(description = "Opens a loom for you")
    void executeSelf(Player player) {
        AdditionalContainerPaper.LOOM.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    @Permission("eternalcore.loom.other")
    @DescriptionDocs(description = "Opens a loom for another player", arguments = "<player>")
    void execute(Player sender, @Arg Player target) {
        AdditionalContainerPaper.LOOM.open(target);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .placeholder("{PLAYER}", sender.getName())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}
