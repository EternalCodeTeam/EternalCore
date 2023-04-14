package com.eternalcode.core.feature.spawn;

import com.eternalcode.annotations.scan.command.DocsDescription;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.shared.PositionAdapter;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "setspawn")
@Permission("eternalcore.setspawn")
public class SetSpawnCommand {

    private final ConfigurationManager configurationManager;
    private final LocationsConfiguration locations;
    private final NoticeService noticeService;

    public SetSpawnCommand(ConfigurationManager configurationManager, LocationsConfiguration locations, NoticeService noticeService) {
        this.configurationManager = configurationManager;
        this.locations = locations;
        this.noticeService = noticeService;
    }

    @Execute
    @DocsDescription(description = "Set spawn location")
    void execute(Player player) {
        this.locations.spawn = PositionAdapter.convert(player.getLocation());

        this.configurationManager.save(this.locations);

        this.noticeService.create()
            .notice(translation -> translation.spawn().spawnSet())
            .player(player.getUniqueId())
            .send();
    }
}
