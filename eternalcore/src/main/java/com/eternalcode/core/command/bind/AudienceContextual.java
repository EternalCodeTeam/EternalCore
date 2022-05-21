package com.eternalcode.core.command.bind;

import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import panda.std.Result;

public class AudienceContextual implements Contextual<CommandSender, Viewer> {

    private final BukkitViewerProvider bukkitViewerProvider;

    public AudienceContextual(BukkitViewerProvider bukkitViewerProvider) {
        this.bukkitViewerProvider = bukkitViewerProvider;
    }

    @Override
    public Result<Viewer, Object> extract(CommandSender sender, LiteInvocation invocation) {
        return Result.ok(this.bukkitViewerProvider.sender(sender));
    }

}
