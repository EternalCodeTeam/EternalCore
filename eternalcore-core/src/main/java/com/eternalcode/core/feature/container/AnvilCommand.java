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

@Command(name = "anvil")
class AnvilCommand {

    private final NoticeService noticeService;

    @Inject
    AnvilCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.anvil")
    @DescriptionDocs(description = "Opens an anvil for you")
    void executeSelf(@Sender Player player) {
        this.openAnvil(player);
    }

    @Execute
    @Permission("eternalcore.anvil.other")
    @DescriptionDocs(description = "Opens an anvil for another player", arguments = "<player>")
    void execute(@Sender CommandSender sender, @Arg Player target) {
        this.openAnvil(target);

        this.noticeService.create()
            .notice(translation -> translation.container().targetAnvilOpened())
            .sender(sender)
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    void openAnvil(Player player) {
        PaperContainer.ANVIL.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().anvilOpened())
            .player(player.getUniqueId())
            .send();
    }

}
