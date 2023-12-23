package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;

@Command(name = "delhome")
@Permission("eternalcore.delhome")
class DelHomeCommand {

    private final HomeManager homeManager;
    private final NoticeService noticeService;

    @Inject
    DelHomeCommand(HomeManager homeManager, NoticeService noticeService) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Delete home", arguments = "<home>")
    void execute(@Context User user, @Arg Home home) {
        this.homeManager.deleteHome(user, home.getName());
        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().delete())
            .placeholder("{HOME}", home.getName())
            .send();
    }

}
