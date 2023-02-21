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

    private final NoticeService announcer;

    public CartographyTableCommand(NoticeService announcer) {
        this.announcer = announcer;
    }

    @Execute
    void executeSelf(Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.CARTOGRAPHY_TABLE);

        this.announcer.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.container().genericContainerOpened())
            .send();
    }

    @Execute
    void execute(Player invoker, @Arg Player target) {
        AdditionalContainerPaper.openAdditionalContainer(target, AdditionalContainerType.CARTOGRAPHY_TABLE);

        this.announcer.create()
            .player(target.getUniqueId())
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .placeholder("{PLAYER}", invoker.getName())
            .send();

        this.announcer.create()
            .player(invoker.getUniqueId())
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}
