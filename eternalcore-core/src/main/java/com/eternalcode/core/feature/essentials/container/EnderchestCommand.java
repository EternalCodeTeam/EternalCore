package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "enderchest", aliases = { "ec" })
class EnderchestCommand {

    private final NoticeService noticeService;

    @Inject
    EnderchestCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    @Permission("eternalcore.enderchest")
    @DescriptionDocs(description = "Opens your enderchest")
    void execute(Player player) {
        player.openInventory(player.getEnderChest());

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    @Permission("eternalcore.enderchest.other")
    @DescriptionDocs(description = "Opens another player his enderchest", arguments = "<player>")
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
