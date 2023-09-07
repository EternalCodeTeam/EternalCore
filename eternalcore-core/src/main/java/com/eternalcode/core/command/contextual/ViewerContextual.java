package com.eternalcode.core.command.contextual;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.CommandSender;
import panda.std.Result;

@LiteContextual(Viewer.class)
public class ViewerContextual implements Contextual<CommandSender, Viewer> {

    private final BukkitViewerProvider bukkitViewerProvider;

    @Inject
    public ViewerContextual(BukkitViewerProvider bukkitViewerProvider) {
        this.bukkitViewerProvider = bukkitViewerProvider;
    }

    @Override
    public Result<Viewer, Object> extract(CommandSender sender, Invocation<CommandSender> invocation) {
        return Result.ok(this.bukkitViewerProvider.sender(sender));
    }

}
