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

    private final NoticeService noticeService;

    public AnvilCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    void executeSelf(Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.ANVIL);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    void execute(Player sender, @Arg Player target) {
        AdditionalContainerPaper.openAdditionalContainer(target, AdditionalContainerType.ANVIL);
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
