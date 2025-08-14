package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Optional;
import java.util.Set;

@Command(name = "delhome")
@Permission("eternalcore.delhome")
class DelHomeCommand {

    private final HomeService homeService;
    private final NoticeService noticeService;

    @Inject
    DelHomeCommand(HomeService homeService, NoticeService noticeService) {
        this.homeService = homeService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Deletes home if player has only one home")
    void execute(@Context User user) {
        Optional<Set<Home>> homes = this.homeService.getHomes(user.getUniqueId());

        if (homes.isEmpty()) {
            this.noticeService.create()
                .user(user)
                .notice(translation -> translation.home().noHomesOwned())
                .send();
            return;
        }

        Set<Home> homeSet = homes.get();

        if (homeSet.size() > 1) {
            this.noticeService.create()
                .user(user)
                .notice(translation -> translation.home().specifyToDelete())
                .send();
            return;
        }

        Home home = homeSet.stream().findFirst().get();
        String name = home.getName();

        this.homeService.deleteHome(user.getUniqueId(), name);

        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().delete())
            .placeholder("{HOME}", name)
            .send();

    }

    @Execute
    @DescriptionDocs(description = "Delete home", arguments = "<home>")
    void execute(@Context User user, @Arg Home home) {
        this.homeService.deleteHome(user.getUniqueId(), home.getName());
        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().delete())
            .placeholder("{HOME}", home.getName())
            .send();
    }
}
