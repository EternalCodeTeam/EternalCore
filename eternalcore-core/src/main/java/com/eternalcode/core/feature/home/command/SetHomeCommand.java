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
import java.util.Optional;
import java.util.Set;
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



        Optional<Home> home = this.homeService.getHome(uniqueId, homeName);
        home.ifPresent(ignored -> {
            this.homeService.setHome(uniqueId, homeName, player.getLocation());

            this.noticeService.create()
                .user(user)
                .placeholder("{HOME}", homeName)
                .notice(translation -> translation.home().overrideHomeLocation())
                .send();

            return;
        });

        if (this.homeService.underLimit(uniqueId)) {
            int homeLimit = this.homeService.getHomeLimit(uniqueId);

            this.noticeService.create()
                .user(user)
                .placeholder("{LIMIT}", String.valueOf(homeLimit))
                .notice(translation -> translation.home().limit())
                .send();

            this.eventCaller.callEvent(new HomeLimitReachedEvent(
                player.getUniqueId(),
                homeLimit,
                this.homeService.getHomes(player.getUniqueId()).get().size()
            ));

            return;
        }

        this.homeService.setHome(uniqueId, homeName, player.getLocation());
        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().create())
            .placeholder("{HOME}", homeName)
            .send();
    }
}
