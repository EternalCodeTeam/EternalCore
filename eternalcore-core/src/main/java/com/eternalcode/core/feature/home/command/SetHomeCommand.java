package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.feature.home.event.HomeCreateEvent;
import com.eternalcode.core.feature.home.event.HomeLimitReachedEvent;
import com.eternalcode.core.feature.home.event.HomeOverrideEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "sethome")
@Permission("eternalcore.sethome")
class SetHomeCommand {

    private final HomeManager homeManager;
    private final NoticeService noticeService;
    private final PluginConfiguration pluginConfiguration;
    private final EventCaller eventCaller;

    @Inject
    SetHomeCommand(
        HomeManager homeManager,
        NoticeService noticeService,
        PluginConfiguration pluginConfiguration,
        EventCaller eventCaller
    ) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
        this.pluginConfiguration = pluginConfiguration;
        this.eventCaller = eventCaller;
    }

    @Execute
    @DescriptionDocs(description = "Set home location with specified name", arguments = "<home>")
    void execute(@Context User user, @Context Player player, @Arg String home) {
        this.setOrOverrideHome(user, player, home);
    }

    @Execute
    @DescriptionDocs(description = "Set home location")
    void execute(@Context User user, @Context Player player) {
        this.setOrOverrideHome(user, player, "home");
    }

    private void setOrOverrideHome(User user, Player player, String homeName) {
        if (this.homeManager.hasHomeWithSpecificName(user, homeName)) {
            this.homeManager.createHome(user, homeName, player.getLocation());

            this.noticeService.create()
                .user(user)
                .placeholder("{HOME}", homeName)
                .notice(translation -> translation.home().overrideHomeLocation())
                .send();

            return;
        }

        int amountOfUserHomes = this.homeManager.getHomes(player.getUniqueId()).size();
        int maxAmountOfUserHomes = this.homeManager.getHomeLimit(player, this.pluginConfiguration.homes);

        if (amountOfUserHomes >= maxAmountOfUserHomes) {
            this.noticeService.create()
                .user(user)
                .placeholder("{LIMIT}", String.valueOf(maxAmountOfUserHomes))
                .notice(translation -> translation.home().limit())
                .send();

            this.eventCaller.callEvent(new HomeLimitReachedEvent(player, maxAmountOfUserHomes, amountOfUserHomes));

            return;
        }

        Home createdHome = this.homeManager.createHome(user, homeName, player.getLocation());
        this.eventCaller.callEvent(new HomeCreateEvent(player, createdHome));

        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().create())
            .placeholder("{HOME}", homeName)
            .send();
    }
}
