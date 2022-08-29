package com.eternalcode.core.command.handler;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import panda.utilities.text.Joiner;

public class PermissionMessage implements PermissionHandler<CommandSender> {

    private final BukkitUserProvider userProvider;
    private final AudienceProvider audienceProvider;
    private final LanguageManager languageManager;
    private final MiniMessage miniMessage;

    public PermissionMessage(BukkitUserProvider userProvider, AudienceProvider audienceProvider, LanguageManager languageManager, MiniMessage miniMessage) {
        this.userProvider = userProvider;
        this.audienceProvider = audienceProvider;
        this.languageManager = languageManager;
        this.miniMessage = miniMessage;
    }

    @Override
    public void handle(CommandSender commandSender, LiteInvocation invocation, RequiredPermissions requiredPermissions) {
        String permissionMessage = this.userProvider.getUser(invocation)
            .map(this.languageManager::getMessages)
            .orElseGet(this.languageManager.getDefaultMessages())
            .argument().permissionMessage();

        String perms = Joiner.on(", ")
            .join(requiredPermissions.getPermissions())
            .toString();

        String replaced = permissionMessage.replace("{PERMISSIONS}", perms);

        if (commandSender instanceof Player player) {
            this.audienceProvider.player(player.getUniqueId()).sendMessage(this.miniMessage.deserialize(replaced));
            return;
        }

        if (commandSender instanceof ConsoleCommandSender || commandSender instanceof RemoteConsoleCommandSender || commandSender instanceof BlockCommandSender) {
            this.audienceProvider.console().sendMessage(this.miniMessage.deserialize(replaced));
        }
    }
}
