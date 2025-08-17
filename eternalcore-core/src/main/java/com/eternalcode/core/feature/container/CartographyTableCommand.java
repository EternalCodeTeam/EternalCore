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

@Command(name = "cartography", aliases = "cartography-table")
class CartographyTableCommand {

    private final NoticeService noticeService;

    @Inject
    CartographyTableCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.cartography")
    @DescriptionDocs(description = "Opens a cartography table for you")
    void executeSelf(@Sender Player player) {
        this.openCartographyTable(player);
    }

    @Execute
    @Permission("eternalcore.cartography.other")
    @DescriptionDocs(description = "Opens a cartography table for another player", arguments = "<player>")
    void execute(@Sender CommandSender sender, @Arg Player target) {
        this.openCartographyTable(target);

        this.noticeService.create()
            .notice(translation -> translation.container().targetCartographyOpened())
            .placeholder("{PLAYER}", target.getName())
            .sender(sender)
            .send();
    }

    void openCartographyTable(Player player) {
        PaperContainer.CARTOGRAPHY_TABLE.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().cartographyOpened())
            .player(player.getUniqueId())
            .send();
    }

}
