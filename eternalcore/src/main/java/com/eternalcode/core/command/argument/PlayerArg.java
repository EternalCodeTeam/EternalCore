package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;

@ArgumentName("player")
public class PlayerArg implements SingleArgumentHandler<Player> {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;
    private final Server server;

    public PlayerArg(BukkitUserProvider userProvider, LanguageManager languageManager, Server server) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
        this.server = server;
    }

    @Override
    public Player parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Player player = this.server.getPlayer(argument);

        if (player == null) {
            Messages messages = userProvider.getUser(invocation)
                .map(languageManager::getMessages)
                .orElseGet(languageManager.getDefaultMessages());

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
