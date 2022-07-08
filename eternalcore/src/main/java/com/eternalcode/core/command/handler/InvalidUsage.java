package com.eternalcode.core.command.handler;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.placeholder.Placeholders;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.scheme.Scheme;
import org.bukkit.command.CommandSender;

public class InvalidUsage implements InvalidUsageHandler<CommandSender> {

    private static final Placeholders<String> SCHEME = Placeholders.of("{USAGE}", scheme -> scheme);


    private final BukkitViewerProvider viewerProvider;
    private final NoticeService noticeService;

    public InvalidUsage(BukkitViewerProvider viewerProvider, NoticeService noticeService) {
        this.viewerProvider = viewerProvider;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Scheme scheme) {
        Viewer viewer = this.viewerProvider.sender(sender);

        if (scheme.getSchemes().size() == 1) {
            this.noticeService.viewer(viewer, messages -> messages.argument().usageMessage(), SCHEME.toFormatter(scheme.getSchemes().get(0)));
            return;
        }

        this.noticeService.viewer(viewer, messages -> messages.argument().usageMessageHead());
        for (String schematic : scheme.getSchemes()) {
            this.noticeService.viewer(viewer, messages -> messages.argument().usageMessageEntry(), SCHEME.toFormatter(schematic));
        }
    }

}
