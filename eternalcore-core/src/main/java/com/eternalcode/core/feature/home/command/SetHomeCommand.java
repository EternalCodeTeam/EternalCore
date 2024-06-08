package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.feature.home.event.HomeCreateEvent;
import com.eternalcode.core.feature.home.event.HomeLimitReachedEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;
import org.bukkit.entity.Player;

@Command(name = "sethome")
@Permission("eternalcore.sethome")
class SetHomeCommand {

    private final HomeService homeService;
    private final NoticeService noticeService;
    private final PluginConfiguration pluginConfiguration;
    private final EventCaller eventCaller;

    @Inject
    SetHomeCommand(
        HomeService homeService,
        NoticeService noticeService,
        PluginConfiguration pluginConfiguration,
        EventCaller eventCaller
    ) {
        this.homeService = homeService;
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
        this.setOrOverrideHome(user, player, this.pluginConfiguration.homes.defaultHomeName);
    }

    private void setOrOverrideHome(User user, Player player, String homeName) {
        UUID uniqueId = user.getUniqueId();

        if (this.homeService.hasHomeWithSpecificName(uniqueId, homeName)) {
            this.homeService.createHome(uniqueId, homeName, player.getLocation());

            this.noticeService.create()
                .user(user)
                .placeholder("{HOME}", homeName)
                .notice(translation -> translation.home().overrideHomeLocation())
                .send();

            return;
        }

        int amountOfUserHomes = this.homeService.getHomes(player.getUniqueId()).size();
        int maxAmountOfUserHomes = this.homeService.getHomeLimit(player, this.pluginConfiguration.homes.maxHomes);

        if (amountOfUserHomes >= maxAmountOfUserHomes) {
            this.noticeService.create()
                .user(user)
                .placeholder("{LIMIT}", String.valueOf(maxAmountOfUserHomes))
                .notice(translation -> translation.home().limit())
                .send();

            this.eventCaller.callEvent(new HomeLimitReachedEvent(player.getUniqueId(), maxAmountOfUserHomes, amountOfUserHomes));

            return;
        }

        Home createdHome = this.homeService.createHome(uniqueId, homeName, player.getLocation());
        this.eventCaller.callEvent(new HomeCreateEvent(player.getUniqueId(), createdHome, createdHome.getLocation()));

        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().create())
            .placeholder("{HOME}", homeName)
            .send();
    }
}
