package com.eternalcode.core.feature.setslot;

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

@Command(name = "setslot")
@Permission("eternalcore.setslot")
public class SetSlotCommand {

    private final SetSlotService setSlotService;
    private final NoticeService noticeService;

    @Inject
    public SetSlotCommand(
        SetSlotService setSlotService,
        NoticeService noticeService
    ) {
        this.setSlotService = setSlotService;
        this.noticeService = noticeService;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Set the max players on the server")
    public void execute(@Context Viewer viewer, @Arg int slots) {
        if (slots <= 0) {
            this.noticeService.create()
                .notice(notice -> notice.argument().numberBiggerThanOrEqualZero())
                .viewer(viewer)
                .send();
            return;
        }

        this.setSlotService.setCapacity(slots);
        this.noticeService.create()
            .notice(notice -> notice.setSlot().slotSaved())
            .placeholder("{SLOTS}", String.valueOf(slots))
            .viewer(viewer)
            .send();
    }
}
