package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;

@Command(name = "sethome")
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

    @Execute
    @DescriptionDocs(description = "Set home location with specified name", arguments = "<home>")
    void execute(@Context User user, @Context Player player, @Arg String home) {
        this.setHome(user, player, home);
    }

    @Execute
    @DescriptionDocs(description = "Set home location")
    void execute(@Context User user, @Context Player player) {
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
