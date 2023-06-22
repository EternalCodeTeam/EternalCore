package com.eternalcode.core.feature.automessage;

import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "automessage", aliases = { "automsg" })
@Permission("eternalcode.automessage")
public class AutoMessageCommand {

    private final AutoMessageService autoMessageService;
    private final NoticeService noticeService;

    public AutoMessageCommand(AutoMessageService autoMessageService, NoticeService noticeService) {
        this.autoMessageService = autoMessageService;
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Player player) {
        this.autoMessageService.switchReciving(player.getUniqueId()).then(reciving -> {
            if (reciving) {
                this.noticeService.create()
                    .notice(messages -> messages.autoMessage().enabled())
                    .send();

                return;
            }

            this.noticeService.create()
                .notice(messages -> messages.autoMessage().disabled())
                .send();
        });
    }
}
