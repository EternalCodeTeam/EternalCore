package com.eternalcode.core.litecommand.argument;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteNativeArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.ArgumentContext;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleOrElseArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.MatchResult;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;
import java.util.List;

@LiteNativeArgument(type = World.class, annotation = Arg.class)
@ArgumentName("world")
class WorldArgument implements SingleOrElseArgument<CommandSender, Arg> {

    private final Server server;
    private final TranslationManager translationManager;
    private final ViewerProvider viewerProvider;

    @Inject
    WorldArgument(Server server, TranslationManager translationManager, ViewerProvider viewerProvider) {
        this.server = server;
        this.translationManager = translationManager;
        this.viewerProvider = viewerProvider;
    }

    @Override
    public MatchResult match(LiteInvocation invocation, ArgumentContext<Arg> context, String argument) {
        World world = this.server.getWorld(argument);

        if (world == null) {
            Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
            Translation translation = this.translationManager.getMessages(viewer);

            return MatchResult.notMatched(translation.argument().worldDoesntExist());
        }

        return MatchResult.matched(world, 1);
    }

    @Override
    public MatchResult orElse(LiteInvocation invocation, ArgumentContext<Arg> context) {
        if (invocation.sender().getHandle() instanceof Player player) {
            return MatchResult.matched(player.getWorld(), 0);
        }

        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
        Translation translation = this.translationManager.getMessages(viewer);

        return MatchResult.notMatched(translation.argument().youMustGiveWorldName());
    }

    @Override
    public List<Suggestion> suggestion(LiteInvocation invocation, Parameter parameter, Arg annotation) {
        return this.server.getWorlds().stream()
            .map(World::getName)
            .map(Suggestion::of)
            .toList();
    }

}
