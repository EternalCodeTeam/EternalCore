package com.eternalcode.core.command.argument;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import panda.std.Result;

import java.util.List;

@ArgumentName("player")
public class UserArgument implements OneArgument<User> {

    private final BukkitViewerProvider viewerProvider;
    private final LanguageManager languageManager;
    private final Server server;
    private final UserManager userManager;

    public UserArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager, Server server, UserManager userManager) {
        this.viewerProvider = viewerProvider;
        this.languageManager = languageManager;
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
    public Result<User, String> parse(LiteInvocation invocation, String argument) {
        return this.userManager.getUser(argument).toResult(() -> {
            Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
            Messages messages = this.languageManager.getMessages(viewer.getLanguage());

            return messages.argument().offlinePlayer();
        });
    }

}
