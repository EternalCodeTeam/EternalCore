package com.eternalcode.core.feature.spawn;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "setspawn")
@Permission("eternalcore.setspawn")
class SetSpawnCommand {

    private final SpawnService spawnService;
    private final NoticeService noticeService;

    @Inject
    SetSpawnCommand(SpawnService spawnService, NoticeService noticeService) {
        this.spawnService = spawnService;
        this.noticeService = noticeService;
    }

    @Async
    @Execute
    @DescriptionDocs(description = "Set spawn location")
    void execute(@Context Player player) {
        this.spawnService.setSpawnLocation(player.getLocation());

        this.noticeService.create()
            .notice(translation -> translation.spawn().spawnSet())
            .player(player.getUniqueId())
            .send();
    }
}
