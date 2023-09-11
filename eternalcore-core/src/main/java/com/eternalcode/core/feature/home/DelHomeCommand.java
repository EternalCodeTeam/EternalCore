package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;

@Route(name = "delhome")
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
    void execute(User user, @Arg Home home) {
        this.homeManager.deleteHome(user, home.getName());
        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().delete())
            .placeholder("{HOME}", home.getName())
            .send();
    }

}
