package com.eternalcode.core.command.binds;

import com.eternalcode.core.configuration.MessagesConfiguration;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;

@ArgumentName("player")
public class PlayerArgument implements SingleArgumentHandler<Player> {

    private final MessagesConfiguration messages;
    private final Server server;

    public PlayerArgument(MessagesConfiguration messages, Server server) {
        this.messages = messages;
        this.server = server;
    }

    @Override
    public Player parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Player player = this.server.getPlayer(argument);

        if (player == null) {
            throw new ValidationCommandException(ValidationInfo.CUSTOM, this.messages.argumentSection.offlinePlayer);
        }

        return player;
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Bukkit.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .toList();
    }
}
