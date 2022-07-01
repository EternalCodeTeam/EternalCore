package com.eternalcode.core.command.argument;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
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

    private final BukkitViewerProvider viewerProvider;
    private final LanguageManager languageManager;
    private final Server server;

    public PlayerArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager, Server server) {
        this.viewerProvider = viewerProvider;
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
            Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
            Messages messages = this.languageManager.getMessages(viewer.getLanguage());

            return Result.error(messages.argument().offlinePlayer());
        }

        return Result.ok(player);
    }

}
