package com.eternalcode.core.command.argument;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import panda.std.Result;

import java.util.List;

@LiteArgument(type = Player.class)
@ArgumentName("player")
public class PlayerArgument extends AbstractViewerArgument<Player> {

    private final Server server;

    @Inject
    public PlayerArgument(ViewerProvider viewerProvider, TranslationManager translationManager, Server server) {
        super(viewerProvider, translationManager);
        this.server = server;
    }

    @Override
    public Result<Player, Notice> parse(LiteInvocation invocation, String argument, Translation translation) {
        Player player = this.server.getPlayer(argument);

        if (player == null) {
            return Result.error(translation.argument().offlinePlayer());
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
