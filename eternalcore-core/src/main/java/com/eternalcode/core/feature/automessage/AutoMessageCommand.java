package com.eternalcode.core.feature.automessage;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "automessage", aliases = { "automsg" })
@Permission("eternalcore.automessage")
class AutoMessageCommand {

    private final AutoMessageService autoMessageService;
    private final NoticeService noticeService;

    @Inject
    AutoMessageCommand(AutoMessageService autoMessageService, NoticeService noticeService) {
        this.autoMessageService = autoMessageService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Toggles the display of automatic messages.")
    void execute(@Context Player player) {
        this.autoMessageService.switchReceiving(player.getUniqueId()).thenAccept(receiving -> {
            if (receiving) {
                this.noticeService.player(player.getUniqueId(), messages -> messages.autoMessage().enabled());

                return;
            }

            this.noticeService.player(player.getUniqueId(), messages -> messages.autoMessage().disabled());
        });
    }
}
