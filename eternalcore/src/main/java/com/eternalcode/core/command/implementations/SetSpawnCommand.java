package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "setspawn")
@Permission("eternalcore.command.setspawn")
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
        this.locations.spawn = player.getLocation().clone();
        this.configurationManager.render(locations);

        this.noticeService
            .notice()
            .message(messages -> messages.other().spawnSet())
            .player(player.getUniqueId())
            .send();
    }
}
