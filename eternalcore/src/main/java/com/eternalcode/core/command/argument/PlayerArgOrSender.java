package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.NotRequiredArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;

@ArgumentName("playerOrSender")
public class PlayerArgOrSender implements NotRequiredArgumentHandler<Player> {

    private final LanguageManager languageManager;
    private final BukkitUserProvider userProvider;
    private final Server server;

    public PlayerArgOrSender(LanguageManager languageManager, BukkitUserProvider userProvider, Server server) {
        this.languageManager = languageManager;
        this.userProvider = userProvider;
        this.server = server;
    }

    @Override
    public Player parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Player player = this.server.getPlayer(argument);

        if (player == null) {
            Audience audience = userProvider.getAudience(invocation);
            Messages messages = languageManager.getMessages(audience.getLanguage());

            throw new ValidationCommandException(messages.argument().offlinePlayer());
        }

        return player;
    }

    @Override
    public Player orElse(LiteInvocation invocation) throws ValidationCommandException {
        if (invocation.sender().getSender() instanceof Player player) {
            return player;
        }

        Messages defaultMessages = languageManager.getDefaultMessages();
        String onlyPlayer = defaultMessages.argument().onlyPlayer();

        throw new ValidationCommandException(onlyPlayer);
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return this.server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .toList();
    }

}
