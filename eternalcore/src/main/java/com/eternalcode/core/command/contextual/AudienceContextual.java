package com.eternalcode.core.command.contextual;

import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.CommandSender;
import panda.std.Result;

public class AudienceContextual implements Contextual<CommandSender, Viewer> {

    private final BukkitViewerProvider bukkitViewerProvider;

    public AudienceContextual(BukkitViewerProvider bukkitViewerProvider) {
        this.bukkitViewerProvider = bukkitViewerProvider;
    }

    @Override
    public Result<Viewer, Object> extract(CommandSender sender, Invocation<CommandSender> invocation) {
        return Result.ok(this.bukkitViewerProvider.sender(sender));
    }

}
