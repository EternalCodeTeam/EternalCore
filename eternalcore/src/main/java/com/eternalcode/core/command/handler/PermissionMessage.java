package com.eternalcode.core.command.handler;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
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

    private final BukkitViewerProvider viewerProvider;
    private final AudienceProvider audienceProvider;
    private final LanguageManager languageManager;
    private final MiniMessage miniMessage;

    public PermissionMessage(BukkitViewerProvider viewerProvider, AudienceProvider audienceProvider, LanguageManager languageManager, MiniMessage miniMessage) {
        this.viewerProvider = viewerProvider;
        this.audienceProvider = audienceProvider;
        this.languageManager = languageManager;
        this.miniMessage = miniMessage;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, RequiredPermissions requiredPermissions) {
        Viewer viewer = this.viewerProvider.sender(sender);
        Messages messages = this.languageManager.getMessages(viewer);

        String perms = Joiner.on(", ")
            .join(requiredPermissions.getPermissions())
            .toString();

        String replaced = messages.argument().permissionMessage().getMessage().replace("{PERMISSIONS}", perms);

        if (sender instanceof Player player) {
            this.audienceProvider.player(player.getUniqueId()).sendMessage(this.miniMessage.deserialize(replaced));
            return;
        }

        if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender || sender instanceof BlockCommandSender) {
            this.audienceProvider.console().sendMessage(this.miniMessage.deserialize(replaced));
        }
    }
}
