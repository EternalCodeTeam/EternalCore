package com.eternalcode.core.feature.container;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "enderchest", aliases = { "ec" })
class EnderchestCommand {

    private final NoticeService noticeService;

    @Inject
    EnderchestCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.enderchest")
    @DescriptionDocs(description = "Opens your enderchest")
    void execute(@Sender Player player) {
        this.openEnderChest(player);
    }

    @Execute
    @Permission("eternalcore.enderchest.other")
    @DescriptionDocs(description = "Opens selected player's enderchest to him", arguments = "<player>")
    void execute(@Sender CommandSender commandSender, @Arg Player target) {
        this.openEnderChest(target);

        this.noticeService.create()
            .notice(translation -> translation.container().targetEnderchestOpened())
            .sender(commandSender)
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    void openEnderChest(Player player) {
        player.openInventory(player.getEnderChest());

        this.noticeService.create()
            .notice(translation -> translation.container().enderchestOpened())
            .player(player.getUniqueId())
            .send();
    }

}
