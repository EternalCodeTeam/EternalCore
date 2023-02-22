package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "enderchest", aliases = { "ec" })
@Permission("eternalcore.enderchest")
public class EnderchestCommand {

    private final NoticeService noticeService;

    public EnderchestCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Player player) {
        player.openInventory(player.getEnderChest());

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    void execute(Player player, @Arg Player target) {
        player.openInventory(target.getEnderChest());

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedFor())
            .placeholder("{PLAYER}", target.getName())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpenedBy())
            .player(target.getUniqueId())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

}
