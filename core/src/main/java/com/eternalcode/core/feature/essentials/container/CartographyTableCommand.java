package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.containers.AdditionalContainerType;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "cartography", aliases = "cartography-table")
@Permission("eternalcore.cartography")
public class CartographyTableCommand {

    private final NoticeService noticeService;

    public CartographyTableCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void executeSelf(Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.CARTOGRAPHY_TABLE);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.container().genericContainerOpened())
            .send();
    }

    @Execute
    void execute(Player sender, @Arg Player target) {
        AdditionalContainerPaper.openAdditionalContainer(target, AdditionalContainerType.CARTOGRAPHY_TABLE);

        this.noticeService.create()
            .player(target.getUniqueId())
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .placeholder("{PLAYER}", sender.getName())
            .send();

        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}
