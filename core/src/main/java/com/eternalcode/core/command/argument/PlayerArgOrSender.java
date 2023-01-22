package com.eternalcode.core.command.argument;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.ArgumentContext;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.MatchResult;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Optional;

@ArgumentName("player")
@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.1.0")
public class PlayerArgOrSender implements Argument<CommandSender, Arg> {

    private final TranslationManager translationManager;
    private final BukkitViewerProvider viewerProvider;
    private final Server server;

    public PlayerArgOrSender(TranslationManager translationManager, BukkitViewerProvider viewerProvider, Server server) {
        this.translationManager = translationManager;
        this.viewerProvider = viewerProvider;
        this.server = server;
    }

    @Override
    public MatchResult match(LiteInvocation invocation, ArgumentContext<Arg> context) {
        if (context.currentArgument() >= invocation.arguments().length) {
            if (invocation.sender().getHandle() instanceof Player player) {
                return MatchResult.matched(player, 0);
            }

            Translation defaultTranslation = this.translationManager.getDefaultMessages();
            Notification onlyPlayer = defaultTranslation.argument().onlyPlayer();

            return MatchResult.notMatched(onlyPlayer);
        }

        Optional<Player> playerOptional = invocation.argument(context.currentArgument())
            .map(this.server::getPlayer);

        if (playerOptional.isEmpty()) {
            Viewer audience = this.viewerProvider.any(invocation.sender().getHandle());
            Translation translation = this.translationManager.getMessages(audience.getLanguage());

            return MatchResult.notMatched(translation.argument().offlinePlayer());
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
