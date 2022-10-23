package com.eternalcode.core.command.handler;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InvalidUsage implements InvalidUsageHandler<CommandSender> {

    private static final Placeholders<String> SCHEME = Placeholders.of("{USAGE}", scheme -> scheme);


    private final BukkitViewerProvider viewerProvider;
    private final NoticeService noticeService;

    public InvalidUsage(BukkitViewerProvider viewerProvider, NoticeService noticeService) {
        this.viewerProvider = viewerProvider;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        Viewer viewer = this.viewerProvider.sender(sender);

        List<String> schematics = schematic.getSchematics();

        if (schematics.size() == 1) {
            this.noticeService.create()
                .viewer(viewer)
                .message(messages -> messages.argument().usageMessage())
                .placeholder(SCHEME, schematics.get(0))
                .send();
            return;
        }

        this.noticeService.viewer(viewer, messages -> messages.argument().usageMessageHead());

        for (String schema : schematics) {
            this.noticeService.viewer(viewer, messages -> messages.argument().usageMessageEntry(), SCHEME.toFormatter(schema));
        }

    }
}
