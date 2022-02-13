package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argmunet.PlayerArgument;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.teleport.TeleportManager;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "spawn")
@Permission("eternalcore.command.spawn")
public class SpawnCommand {

    private final LocationsConfiguration locations;
    private final TeleportManager teleportManager;
    private final MessagesConfiguration messages;

    public SpawnCommand(LocationsConfiguration locations, MessagesConfiguration messages, TeleportManager teleportManager) {
        this.teleportManager = teleportManager;
        this.locations = locations;
        this.messages = messages;
    }

    @Execute
    public void execute(Player sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption){
        Location location = this.locations.spawn;

        if (location == null) {
            sender.sendMessage(ChatUtils.color(this.messages.otherMessages.spawnNoSet));
            return;
        }

        if (playerOption.isEmpty()) {
            if (sender.hasPermission("eternalcore.teleport.bypass")) {
                sender.teleportAsync(location);

                sender.sendMessage(ChatUtils.color(this.messages.teleportSection.teleported));
                return;
            }
            if (this.teleportManager.inTeleport(sender.getUniqueId())) {
                sender.sendMessage(ChatUtils.color(this.messages.teleportSection.haveTeleport));
                return;
            }

            this.teleportManager.createTeleport(sender.getUniqueId(), location, 10);

            sender.sendMessage(ChatUtils.color(this.messages.teleportSection.teleporting));
            return;
        }
        Player player = playerOption.get();

        player.teleportAsync(location);

        player.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.spawnTeleportedBy, "{NICK}", sender.getName())));
        sender.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.spawnTeleportedOther, "{NICK}", player.getName())));
    }
}
