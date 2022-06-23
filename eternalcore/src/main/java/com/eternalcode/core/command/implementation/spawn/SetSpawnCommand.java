package com.eternalcode.core.command.implementation.spawn;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.shared.PositionAdapter;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;

@Section(route = "setspawn")
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
    public void execute(Player player){
        this.locations.spawn = PositionAdapter.convert(player.getLocation());

        this.configurationManager.render(this.locations);

        this.noticeService
            .notice()
            .message(messages -> messages.other().spawnSet())
            .player(player.getUniqueId())
            .send();
    }
}
