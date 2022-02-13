package com.eternalcode.core.command.argument;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
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
            throw new ValidationCommandException(this.messages.argumentSection.offlinePlayer);
        }

        return player;
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return this.server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .toList();
    }
}
