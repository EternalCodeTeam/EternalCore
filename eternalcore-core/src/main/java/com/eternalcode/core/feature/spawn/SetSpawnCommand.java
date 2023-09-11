package com.eternalcode.core.feature.spawn;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.shared.PositionAdapter;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "setspawn")
@Permission("eternalcore.setspawn")
class SetSpawnCommand {

    private final ConfigurationManager configurationManager;
    private final LocationsConfiguration locations;
    private final NoticeService noticeService;

    @Inject
    SetSpawnCommand(ConfigurationManager configurationManager, LocationsConfiguration locations, NoticeService noticeService) {
        this.configurationManager = configurationManager;
        this.locations = locations;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Set spawn location")
    void execute(Player player) {
        this.locations.spawn = PositionAdapter.convert(player.getLocation());

        this.configurationManager.save(this.locations);

        this.noticeService.create()
            .notice(translation -> translation.spawn().spawnSet())
            .player(player.getUniqueId())
            .send();
    }
}
