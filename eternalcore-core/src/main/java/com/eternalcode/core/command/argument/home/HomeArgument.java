package com.eternalcode.core.command.argument.home;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.injector.annotations.lite.LiteNativeArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
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

@LiteNativeArgument(type = Home.class, annotation = ArgHome.class)
@ArgumentName("name")
public class HomeArgument implements SingleOrElseArgument<CommandSender, ArgHome> {

    private final HomeManager homeManager;
    private final BukkitViewerProvider viewerProvider;
    private final TranslationManager translationManager;

    public HomeArgument(HomeManager homeManager, BukkitViewerProvider viewerProvider, TranslationManager translationManager) {
        this.homeManager = homeManager;
        this.viewerProvider = viewerProvider;
        this.translationManager = translationManager;
    }

    @Override
    public MatchResult match(LiteInvocation invocation, ArgumentContext<ArgHome> context, String argument) {
        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
        Translation translation = this.translationManager.getMessages(viewer);

        Home home = this.homeManager.getHome(viewer.getUniqueId(), argument)
            .orThrow(() -> new LiteException(translation.home().notExist()));

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
