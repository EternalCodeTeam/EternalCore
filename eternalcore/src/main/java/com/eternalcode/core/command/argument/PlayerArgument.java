package com.eternalcode.core.command.argument;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import panda.std.Result;

import java.util.List;

@ArgumentName("player")
public class PlayerArgument extends AbstractViewerArgument<Player> {

    private final Server server;

    public PlayerArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager, Server server) {
        super(viewerProvider, languageManager);
        this.server = server;
    }

    @Override
    public Result<Player, Notification> parse(LiteInvocation invocation, String argument, Messages messages) {
        Player player = this.server.getPlayer(argument);

        if (player == null) {
            return Result.error(messages.argument().offlinePlayer());
        }

        return Result.ok(player);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .map(Suggestion::of)
            .toList();
    }
}
