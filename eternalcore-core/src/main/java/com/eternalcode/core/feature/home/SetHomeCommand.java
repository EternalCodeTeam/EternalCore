package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "sethome")
@Permission("eternalcore.sethome")
class SetHomeCommand {

    private final HomeManager homeManager;
    private final NoticeService noticeService;
    private final PluginConfiguration pluginConfiguration;

    @Inject
    SetHomeCommand(HomeManager homeManager, NoticeService noticeService, PluginConfiguration pluginConfiguration) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute(required = 1)
    @DescriptionDocs(description = "Set home location with specified name", arguments = "<home>")
    void execute(User user, Player player, @Arg String home) {
        this.setHome(user, player, home);
    }

    @Execute(required = 0)
    @DescriptionDocs(description = "Set home location")
    void execute(User user, Player player) {
        this.setHome(user, player, "home");
    }

    private void setHome(User user, Player player, String home) {
        if (this.homeManager.hasHomeWithSpecificName(user, home)) {
            this.homeManager.createHome(user, home, player.getLocation());

            this.noticeService.create()
                .user(user)
                .placeholder("{HOME}", home)
                .notice(translation -> translation.home().overrideHomeLocation())
                .send();

            return;
        }

        int amountOfUserHomes = this.homeManager.getHomes(player.getUniqueId()).size();
        int maxAmountOfUserHomes = this.homeManager.getHomesLimit(player, this.pluginConfiguration.homes);

        if (amountOfUserHomes >= maxAmountOfUserHomes) {
            this.noticeService.create()
                .user(user)
                .placeholder("{LIMIT}", String.valueOf(maxAmountOfUserHomes))
                .notice(translation -> translation.home().limit())
                .send();

            return;
        }

        this.homeManager.createHome(user, home, player.getLocation());

        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().create())
            .placeholder("{HOME}", home)
            .send();
    }
}
