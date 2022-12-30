package com.eternalcode.core.home.command;

import com.eternalcode.core.home.Home;
import com.eternalcode.core.home.HomeManager;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.ArgumentContext;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleOrElseArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.MatchResult;
import dev.rollczi.litecommands.handle.LiteException;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("name")
public class HomeArgument implements SingleOrElseArgument<CommandSender, ArgHome> {

    private final HomeManager homeManager;
    private final BukkitViewerProvider viewerProvider;
    private final LanguageManager languageManager;

    public HomeArgument(HomeManager homeManager, BukkitViewerProvider viewerProvider, LanguageManager languageManager) {
        this.homeManager = homeManager;
        this.viewerProvider = viewerProvider;
        this.languageManager = languageManager;
    }

    @Override
    public MatchResult match(LiteInvocation invocation, ArgumentContext<ArgHome> context, String argument) {
        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
        Messages messages = this.languageManager.getMessages(viewer);

        Home home = this.homeManager.getHome(viewer.getUniqueId(), argument)
            .orThrow(() -> new LiteException(messages.home().notExist()));

        return MatchResult.matchedSingle(home);
    }

    @Override
    public MatchResult orElse(LiteInvocation invocation, ArgumentContext<ArgHome> context) {
        return this.match(invocation, context, "home");
    }

    @Override
    public List<Suggestion> suggestion(LiteInvocation invocation, Parameter parameter, ArgHome annotation) {
        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());

        return this.homeManager.getHomes(viewer.getUniqueId())
            .stream()
            .map(Home::getName)
            .map(Suggestion::of)
            .collect(Collectors.toList());
    }

}
