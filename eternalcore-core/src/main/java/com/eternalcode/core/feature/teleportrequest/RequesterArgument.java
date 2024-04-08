package com.eternalcode.core.feature.teleportrequest;

import com.eternalcode.core.bridge.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerService;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Objects;

@LiteArgument(type = Player.class, name = RequesterArgument.KEY)
class RequesterArgument extends AbstractViewerArgument<Player> {

    static final String KEY = "requester";

    private final TeleportRequestService requestService;
    private final Server server;

    @Inject
    RequesterArgument(TeleportRequestService requestService, TranslationManager translationManager, ViewerService viewerService, Server server) {
        super(viewerService, translationManager);
        this.requestService = requestService;
        this.server = server;
    }

    @Override
    public ParseResult<Player> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Player target = this.server.getPlayer(argument);

        if (!(invocation.sender() instanceof Player player)) {
            return ParseResult.failure(translation.argument().onlyPlayer());
        }

        if (target == null || !this.requestService.hasRequest(target.getUniqueId(), player.getUniqueId())) {
            return ParseResult.failure(translation.tpa().tpaDenyNoRequestMessage());
        }

        return ParseResult.success(target);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Player> argument, SuggestionContext context) {
        if (!(invocation.sender() instanceof Player player)) {
            return SuggestionResult.empty();
        }

        return this.requestService.findRequests(player.getUniqueId()).stream()
            .map(this.server::getPlayer)
            .filter(Objects::nonNull)
            .map(HumanEntity::getName)
            .collect(SuggestionResult.collector());
    }

}
