package com.eternalcode.core.command.argument;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.teleport.request.TeleportRequestService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import panda.std.Result;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ArgumentName("player")
public class RequesterArgument extends AbstractViewerArgument<Player> {

    private final TeleportRequestService requestService;

    private final Server server;

    public RequesterArgument(TeleportRequestService requestService, TranslationManager translationManager, BukkitViewerProvider viewerProvider, Server server) {
        super(viewerProvider, translationManager);
        this.requestService = requestService;
        this.server = server;
    }

    @Override
    public Result<Player, Notification> parse(LiteInvocation invocation, String argument, Translation translation) {
        Player target = this.server.getPlayer(argument);

        if (!(invocation.sender().getHandle() instanceof Player player)) {
            return Result.error(translation.argument().onlyPlayer());
        }

        if (target == null || !this.requestService.hasRequest(target.getUniqueId(), player.getUniqueId())) {
            return Result.error(translation.tpa().tpaDenyNoRequestMessage());
        }

        return Result.ok(target);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        if (!(invocation.sender().getHandle() instanceof Player player)) {
            return Collections.emptyList();
        }

        return this.requestService.findRequests(player.getUniqueId()).stream()
            .map(this.server::getPlayer)
            .filter(Objects::nonNull)
            .map(HumanEntity::getName)
            .map(Suggestion::of)
            .toList();
    }

}
