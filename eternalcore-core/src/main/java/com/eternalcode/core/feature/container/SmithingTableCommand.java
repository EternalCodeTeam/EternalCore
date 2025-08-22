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

@Command(name = "smithingtable", aliases = "smithing")
class SmithingTableCommand {

    private final NoticeService noticeService;

    @Inject
    SmithingTableCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.smithingtable")
    @DescriptionDocs(description = "Opens a smithing table for you")
    void executeSelf(@Sender Player player) {
        this.openSmithingTable(player);
    }

    @Execute
    @Permission("eternalcore.smithingtable.other")
    @DescriptionDocs(description = "Opens a smithing table for another player", arguments = "<player>")
    void execute(@Sender CommandSender commandSender, @Arg Player target) {
        this.openSmithingTable(target);

        this.noticeService.create()
            .notice(translation -> translation.container().targetSmithingOpened())
            .sender(commandSender)
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    void openSmithingTable(Player player) {
        PaperContainer.SMITHING_TABLE.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().smithingOpened())
            .player(player.getUniqueId())
            .send();
    }

}
