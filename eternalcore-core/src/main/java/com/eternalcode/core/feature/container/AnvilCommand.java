package com.eternalcode.core.feature.container;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
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
    void executeSelf(@Context Player player) {
        AdditionalContainerPaper.ANVIL.open(player);

        this.noticeService.create()
            .notice(translation -> translation.container().genericContainerOpened())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.anvil.other")
    @DescriptionDocs(description = "Opens an anvil for another player", arguments = "<player>")
    void execute(@Context Player sender, @Arg Player target) {
        AdditionalContainerPaper.ANVIL.open(target);

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
