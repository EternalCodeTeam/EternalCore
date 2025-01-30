package com.eternalcode.core.feature.servercapacity;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

@Command(name = "servercapacity")
@Permission("eternalcore.servercapacity")
public class ServerCapacityCommand {

    private final ServerCapacityService serverCapacityService;
    private final NoticeService noticeService;

    @Inject
    public ServerCapacityCommand(
        ServerCapacityService serverCapacityService,
        NoticeService noticeService
    ) {
        this.serverCapacityService = serverCapacityService;
        this.noticeService = noticeService;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Set the server capacity")
    public void execute(@Context Viewer viewer, @Arg int slots) {
        this.serverCapacityService.setCapacity(slots);
        this.noticeService.create()
            .notice(notice -> notice.serverCapacity().slotSaved())
            .placeholder("{SLOTS}", String.valueOf(slots))
            .viewer(viewer)
            .send();
    }
}
