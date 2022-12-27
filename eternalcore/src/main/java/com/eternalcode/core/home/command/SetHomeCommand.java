package com.eternalcode.core.home.command;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.home.HomeManager;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.injector.Inject;
import org.bukkit.entity.Player;

@Route(name = "sethome")
public class SetHomeCommand {

    private final HomeManager homeManager;
    private final NoticeService noticeService;

    @Inject
    public SetHomeCommand(HomeManager homeManager, NoticeService noticeService) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
    }

    @Execute(required = 1)
    void execute(User user, Player player, @Arg @Name("home") String home) {
        this.setHome(user, player, home);
    }

    @Execute(required = 0)
    void execute(User user, Player player) {
        this.setHome(user, player, "home");
    }

    private void setHome(User user, Player player, String home) {
        this.homeManager.createHome(user, home, player.getLocation());
        this.noticeService.create()
            .user(user)
            .notice(messages -> messages.home().create())
            .placeholder("{HOME}", home)
            .send();
    }

}
