package com.eternalcode.core.command.handler;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.scheme.Scheme;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public class InvalidUsage implements InvalidUsageHandler<CommandSender> {

    private final MiniMessage miniMessage;
    private final AudienceProvider audienceProvider;
    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public InvalidUsage(MiniMessage miniMessage, AudienceProvider audienceProvider, BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.miniMessage = miniMessage;
        this.audienceProvider = audienceProvider;
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Scheme scheme) {
        String message = this.userProvider.getUser(invocation)
            .map(this.languageManager::getMessages)
            .orElseGet(this.languageManager.getDefaultMessages())
            .argument().usageMessage();

        String replace = message.replace("{USAGE}", scheme.getSchemes().get(0));

        if (sender instanceof Player player) {
            this.audienceProvider.player(player.getUniqueId()).sendMessage(miniMessage.deserialize(replace));
            return;
        }

        if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender || sender instanceof BlockCommandSender) {
            this.audienceProvider.console().sendMessage(miniMessage.deserialize(replace));
        }
    }

}
