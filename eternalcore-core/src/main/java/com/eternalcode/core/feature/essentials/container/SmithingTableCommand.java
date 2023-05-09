package com.eternalcode.core.feature.essentials.container;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "smithingtable", aliases = "smithing")
@Permission("eternalcore.smithingtable")
public class SmithingTableCommand {

    private final NoticeService noticeService;

    public SmithingTableCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    @DescriptionDocs(description = "Opens a smithing table for you")
    void executeSelf(Player player) {
        AdditionalContainerPaper.SMITHING_TABLE.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    @DescriptionDocs(description = "Opens a smithing table for another player", arguments = "<player>")
    void execute(Player sender, @Arg Player target) {
        AdditionalContainerPaper.SMITHING_TABLE.open(target);

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
