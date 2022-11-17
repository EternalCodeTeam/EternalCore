package com.eternalcode.core.chat.feature.ignore;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.injector.Inject;

@Route(name = "ignore")
@Permission("eternalcore.ignore")
public class IgnoreCommand {

    private final IgnoreRepository repository;
    private final NoticeService noticeService;

    @Inject
    public IgnoreCommand(IgnoreRepository repository, NoticeService noticeService) {
        this.repository = repository;
        this.noticeService = noticeService;
    }

    @Execute
    void ignore(User sender, @Arg User target) {
        this.repository.ignore(sender.getUniqueId(), target.getUniqueId());
        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .message(messages -> messages.privateMessage().ignorePlayer())
            .send();
    }

}
