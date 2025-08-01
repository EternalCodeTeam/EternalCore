package com.eternalcode.core.bridge.litecommand.handler;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.viewer.ViewerService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;

@LiteHandler(InvalidUsage.class)
class InvalidUsageHandlerImpl implements InvalidUsageHandler<CommandSender> {

    private static final Placeholders<String> SCHEME = Placeholders.of("{USAGE}", scheme -> scheme);

    private final ViewerService viewerService;
    private final NoticeService noticeService;

    @Inject
    InvalidUsageHandlerImpl(ViewerService viewerService, NoticeService noticeService) {
        this.viewerService = viewerService;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        Viewer viewer = this.viewerService.any(invocation.sender());
        Schematic schematic = result.getSchematic();

        if (schematic.isOnlyFirst()) {
            this.noticeService.create()
                .viewer(viewer)
                .notice(translation -> translation.argument().usageMessage())
                .placeholders(SCHEME, schematic.first())
                .send();
            return;
        }

        this.noticeService.viewer(viewer, translation -> translation.argument().usageMessageHead());

        for (String schema : schematic.all()) {
            this.noticeService.viewer(viewer, translation -> translation.argument().usageMessageEntry(), SCHEME.toFormatter(schema));
        }
    }

}
