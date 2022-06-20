package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.teleport.request.TeleportRequestService;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.sugesstion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Result;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ArgumentName("player")
public class RequesterArgument implements OneArgument<Player> {

    private final TeleportRequestService requestService;
    private final LanguageManager languageManager;
    private final BukkitUserProvider userProvider;
    private final Server server;

    public RequesterArgument(TeleportRequestService requestService, LanguageManager languageManager, BukkitUserProvider userProvider, Server server) {
        this.requestService = requestService;
        this.languageManager = languageManager;
        this.userProvider = userProvider;
        this.server = server;
    }

    @Override
    public Result<Player, ?> parse(LiteInvocation invocation, String argument) {
        Player target = this.server.getPlayer(argument);

        if (!(invocation.sender().getHandle() instanceof Player player)) {
            return Result.error(this.languageManager.getDefaultMessages().argument().onlyPlayer());
        }

        if (target == null || !this.requestService.hasRequest(target.getUniqueId(), player.getUniqueId())) {

            Messages messages = this.userProvider.getUser(invocation)
                .map(this.languageManager::getMessages)
                .orElseGet(this.languageManager.getDefaultMessages());

            return Result.error(messages.tpa().tpaDenyNoRequestMessage());
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
            .map(Player::getName)
            .map(Suggestion::of)
            .toList();
    }
}
