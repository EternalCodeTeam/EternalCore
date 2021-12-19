package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class TeleportCommand {
    private final ConfigurationManager configurationManager;

    public TeleportCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "teleport",
        aliases = "tp",
        permission = "eternalcore.command.teleport",
        usage = "&8» &cPoprawne użycie &7/teleport <player>",
        acceptsExceeded = true
    )

    public void execute(EternalCore eternalCore, Player player, String[] args, CommandInfo commandInfo) {
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        when(args.length != 1, commandInfo.getUsageMessage());
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer != null) {
            Bukkit.getScheduler().runTaskAsynchronously(eternalCore, () -> player.teleport(targetPlayer));
            return;
        }

        player.sendMessage(ChatUtils.color(config.offlinePlayer));
    }
}

