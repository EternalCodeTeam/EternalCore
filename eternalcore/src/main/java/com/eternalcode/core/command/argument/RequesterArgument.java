package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.teleport.TeleportRequestService;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;

@ArgumentName("player")
public class RequesterArgument implements SingleArgumentHandler<Player> {

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
    public Player parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Player player = (Player) invocation.sender().getSender();
        Player target = this.server.getPlayer(argument);

        if (!this.requestService.hasRequest(target.getUniqueId(), player.getUniqueId())) {

            Messages messages = this.userProvider.getUser(invocation)
                .map(this.languageManager::getMessages)
                .orElseGet(this.languageManager.getDefaultMessages());

            throw new ValidationCommandException(messages.tpa().tpaDenyNoRequestMessage());
        }

        return target;
    }

    @Override
    public List<String> tabulation(LiteInvocation invocation, String command, String[] args) {
        Player player = (Player) invocation.sender().getSender();

        return this.requestService
            .findRequests(player.getUniqueId())
            .stream()
            .map(this.server::getPlayer)
            .map(HumanEntity::getName)
            .toList();
    }
}
