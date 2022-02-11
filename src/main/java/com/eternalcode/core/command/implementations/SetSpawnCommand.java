package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "setspawn")
@Permission("eternalcore.command.setspawn")
public class SetSpawnCommand {

    private final ConfigurationManager configurationManager;
    private final LocationsConfiguration locations;
    private final MessagesConfiguration messages;

    public SetSpawnCommand(ConfigurationManager configurationManager, LocationsConfiguration locations, MessagesConfiguration messages) {
        this.configurationManager = configurationManager;
        this.locations = locations;
        this.messages = messages;
    }

    @Execute
    public void execute(Player player){
        this.locations.spawn = player.getLocation().clone();
        this.configurationManager.render(locations);

        player.sendMessage(ChatUtils.color(this.messages.otherMessages.spawnSet));
    }
}
