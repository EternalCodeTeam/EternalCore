package com.eternalcode.core.feature.spawn;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "setspawn")
@Permission("eternalcore.setspawn")
class SetSpawnCommand {

    private final NoticeService noticeService;
    private final SpawnService spawnService;

    @Inject
    SetSpawnCommand(
        NoticeService noticeService,
        SpawnService spawnService
    ) {
        this.noticeService = noticeService;
        this.spawnService = spawnService;
    }

    @Async
    @Execute
    @DescriptionDocs(description = "Set spawn location")
    void execute(@Sender Player player) {
        this.spawnService.setSpawnLocation(player.getLocation());

        this.noticeService.create()
            .notice(translation -> translation.spawn().spawnSet())
            .player(player.getUniqueId())
            .send();
    }
}
