package com.eternalcode.core.feature.invsee;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;

@Command(name = "invsee")
@Permission("eternalcore.invsee")
public class InvseeCommand {

    private final NoticeService noticeService;
    private final InvseeService invseeService;

    @Inject
    InvseeCommand(NoticeService noticeService, InvseeService invseeService) {
        this.noticeService = noticeService;
        this.invseeService = invseeService;
    }

    @Execute
    @DescriptionDocs(description = "Inspect someone's inventory", arguments = "<player>")
    void invsee(@Context User sender, @Arg("inspected_player") User target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        if (sender.equals(target)) {
            this.noticeService.viewer(sender, translation -> translation.invsee().cannotInspectSelf());
        }

        this.invseeService.openInventory(senderUuid, targetUuid);
    }
}
