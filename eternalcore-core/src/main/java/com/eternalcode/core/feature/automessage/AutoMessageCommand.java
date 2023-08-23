package com.eternalcode.core.feature.automessage;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "automessage", aliases = { "automsg" })
@Permission("eternalcore.automessage")
public class AutoMessageCommand {

    private final AutoMessageService autoMessageService;
    private final NoticeService noticeService;

    public AutoMessageCommand(AutoMessageService autoMessageService, NoticeService noticeService) {
        this.autoMessageService = autoMessageService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Toggles the display of automatic messages.")
    void execute(Player player) {
        this.autoMessageService.switchReciving(player.getUniqueId()).then(receiving -> {
            if (receiving) {
                this.noticeService.player(player.getUniqueId(), messages -> messages.autoMessage().enabled());

                return;
            }

            this.noticeService.player(player.getUniqueId(), messages -> messages.autoMessage().disabled());
        });
    }
}
