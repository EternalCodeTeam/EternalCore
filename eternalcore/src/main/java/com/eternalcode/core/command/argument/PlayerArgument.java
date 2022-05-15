package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.sugesstion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import panda.std.Result;

import java.util.List;

@ArgumentName("player")
public class PlayerArgument implements OneArgument<Player> {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;
    private final Server server;

    public PlayerArgument(BukkitUserProvider userProvider, LanguageManager languageManager, Server server) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
        this.server = server;
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .map(Suggestion::of)
            .toList();
    }

    @Override
    public Result<Player, ?> parse(LiteInvocation invocation, String argument) {
        Player player = this.server.getPlayer(argument);

        if (player == null) {
            Messages messages = this.userProvider.getUser(invocation)
                .map(this.languageManager::getMessages)
                .orElseGet(this.languageManager.getDefaultMessages());

            return Result.error(messages.argument().offlinePlayer());
        }

        return Result.ok(player);
    }

}
