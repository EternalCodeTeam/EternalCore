package com.eternalcode.core.bridge.litecommand.contextual;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.viewer.ViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.context.ContextProvider;
import dev.rollczi.litecommands.context.ContextResult;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

@LiteContextual(Viewer.class)
class ViewerContextual implements ContextProvider<CommandSender, Viewer> {

    private final ViewerProvider bukkitViewerProvider;

    @Inject
    ViewerContextual(ViewerProvider bukkitViewerProvider) {
        this.bukkitViewerProvider = bukkitViewerProvider;
    }

    @Override
    public ContextResult<Viewer> provide(Invocation<CommandSender> invocation) {
        return ContextResult.ok(() -> this.bukkitViewerProvider.any(invocation.sender()));
    }

}
