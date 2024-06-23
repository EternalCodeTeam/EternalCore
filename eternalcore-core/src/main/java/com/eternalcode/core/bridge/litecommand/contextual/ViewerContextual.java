package com.eternalcode.core.bridge.litecommand.contextual;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.viewer.ViewerService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.context.ContextProvider;
import dev.rollczi.litecommands.context.ContextResult;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

@LiteContextual(Viewer.class)
class ViewerContextual implements ContextProvider<CommandSender, Viewer> {

    private final ViewerService bukkitViewerService;

    @Inject
    ViewerContextual(ViewerService bukkitViewerService) {
        this.bukkitViewerService = bukkitViewerService;
    }

    @Override
    public ContextResult<Viewer> provide(Invocation<CommandSender> invocation) {
        return ContextResult.ok(() -> this.bukkitViewerService.any(invocation.sender()));
    }

}
