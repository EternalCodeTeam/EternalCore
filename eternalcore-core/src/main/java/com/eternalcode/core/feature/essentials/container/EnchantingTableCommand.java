package com.eternalcode.core.feature.essentials.container;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.containers.AdditionalContainerType;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "enchantingtable", aliases = "enchanting")
@Permission("eternalcore.enchantingtable")
public class EnchantingTableCommand {

    private final NoticeService noticeService;

    public EnchantingTableCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    @DescriptionDocs(description = "Opens a enchanting table for you")
    void executeSelf(Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.ENCHANTING_TABLE);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    @DescriptionDocs(description = "Opens a enchanting table for another player", arguments = "<player>")
    void execute(Player sender, @Arg Player target) {
        AdditionalContainerPaper.openAdditionalContainer(target, AdditionalContainerType.ENCHANTING_TABLE);

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
