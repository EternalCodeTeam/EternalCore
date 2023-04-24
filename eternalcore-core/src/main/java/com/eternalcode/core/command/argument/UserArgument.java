package com.eternalcode.core.command.argument;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import panda.std.Result;

import java.util.List;
import java.util.Optional;

@ArgumentName("player")
public class UserArgument extends AbstractViewerArgument<User> {

    private final Server server;
    private final UserManager userManager;

    public UserArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager, Server server, UserManager userManager) {
        super(viewerProvider, translationManager);
        this.server = server;
        this.userManager = userManager;
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .map(Suggestion::of)
            .toList();
    }

    @Override
    public Result<User, Notification> parse(LiteInvocation invocation, String argument, Translation translation) {
        Optional<User> optionalUser = this.userManager.getUser(argument);

        if (optionalUser.isPresent()) {
            return Result.ok(optionalUser.get());
        }

        return Result.error(translation.argument().offlinePlayer());
    }

}
