package com.eternalcode.core.command.argument;

import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;

@ArgumentName("player")
public class PlayerArgument implements SingleArgumentHandler<Player> {

    private final LanguageManager languageManager;
    private final Server server;

    public PlayerArgument(LanguageManager languageManager, Server server) {
        this.languageManager = languageManager;
        this.server = server;
    }

    @Override
    public Player parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Player player = this.server.getPlayer(argument);

        if (player == null) {
            CommandSender sender = (CommandSender) invocation.sender().getSender();
            Messages messages = this.languageManager.getMessages(sender);

            throw new ValidationCommandException(messages.argument().offlinePlayer());
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
