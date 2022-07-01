package com.eternalcode.core.command.argument;

import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.ArgumentContext;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.MatchResult;
import dev.rollczi.litecommands.command.sugesstion.Suggestion;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Optional;

@ArgumentName("player")
public class PlayerArgOrSender implements Argument<CommandSender, Arg> {

    private final LanguageManager languageManager;
    private final BukkitViewerProvider viewerProvider;
    private final Server server;

    public PlayerArgOrSender(LanguageManager languageManager, BukkitViewerProvider viewerProvider, Server server) {
        this.languageManager = languageManager;
        this.viewerProvider = viewerProvider;
        this.server = server;
    }

    @Override
    public MatchResult match(LiteInvocation invocation, ArgumentContext<Arg> context) {
        if (context.currentArgument() >= invocation.arguments().length) {
            if (invocation.sender().getHandle() instanceof Player player) {
                return MatchResult.matched(player, 0);
            }

            Messages defaultMessages = this.languageManager.getDefaultMessages();
            String onlyPlayer = defaultMessages.argument().onlyPlayer();

            return MatchResult.notMatched(onlyPlayer);
        }

        Optional<Player> playerOptional = invocation.argument(context.currentArgument())
            .map(this.server::getPlayer);

        if (playerOptional.isEmpty()) {
            Viewer audience = this.viewerProvider.any(invocation.sender().getHandle());
            Messages messages = this.languageManager.getMessages(audience.getLanguage());

            return MatchResult.notMatched(messages.argument().offlinePlayer());
        }

        return MatchResult.matched(playerOptional.get(), 1);
    }

    @Override
    public List<Suggestion> suggestion(LiteInvocation invocation, Parameter parameter, Arg annotation) {
        return this.server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .map(Suggestion::of)
            .toList();
    }

    @Override
    public boolean isOptional() {
        return false;
    }

}
