package com.eternalcode.core.feature.essentials.container;

import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.containers.AdditionalContainerType;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "anvil")
@Permission("eternalcore.anvil")
public class AnvilCommand {

    private final NoticeService announcer;

    public AnvilCommand(NoticeService announcer) {
        this.announcer = announcer;
    }

    @Execute
    void executeSelf(Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.ANVIL);

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    void execute(Player invoker, @Arg Player target) {
        AdditionalContainerPaper.openAdditionalContainer(target, AdditionalContainerType.ANVIL);
        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .placeholder("{PLAYER}", invoker.getName())
            .player(target.getUniqueId())
            .send();

        this.announcer.create()
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .player(invoker.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}
