package com.eternalcode.core.feature.lag;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Server;
import org.bukkit.World;

@Command(name = "lag", aliases = {"tps"})
@Permission("eternalcore.lag")
class LagCommand {

    private final NoticeService noticeService;
    private final Server server;

    @Inject
    LagCommand(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Shows server performance information")
    void execute(@Sender Viewer viewer) {
        double[] tps = this.server.getTPS();
        Duration uptime = Duration.ofMillis(ManagementFactory.getRuntimeMXBean().getUptime());

        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        long freeMemory = runtime.freeMemory();

        this.noticeService.create()
            .placeholder("{TPS-1M}", LagStatsFormatter.formatTps(tps[0]))
            .placeholder("{TPS-5M}", LagStatsFormatter.formatTps(tps[1]))
            .placeholder("{TPS-15M}", LagStatsFormatter.formatTps(tps[2]))
            .placeholder("{MSPT}", LagStatsFormatter.formatMspt(this.server.getAverageTickTime()))
            .placeholder("{MEMORY-USED}", LagStatsFormatter.formatMemoryMb(usedMemory))
            .placeholder("{MEMORY-MAX}", LagStatsFormatter.formatMemoryMb(maxMemory))
            .placeholder("{MEMORY-FREE}", LagStatsFormatter.formatMemoryMb(freeMemory))
            .placeholder("{UPTIME}", DurationUtil.format(uptime, true))
            .placeholder("{ONLINE}", String.valueOf(this.server.getOnlinePlayers().size()))
            .placeholder("{MAX-PLAYERS}", String.valueOf(this.server.getMaxPlayers()))
            .placeholder("{CORES}", String.valueOf(runtime.availableProcessors()))
            .messages(translation -> this.worldLines(translation.lag().info(), translation.lag().worldsHeader(),
                translation.lag().worldEntry()))
            .viewer(viewer)
            .send();
    }

    private List<String> worldLines(List<String> info, String worldsHeader, String worldEntryTemplate) {
        List<String> lines = new ArrayList<>(info);
        lines.add(worldsHeader);

        for (World world : this.server.getWorlds()) {
            WorldStats stats = WorldStatsCollector.collect(world);
            lines.add(LagStatsFormatter.formatWorldEntry(worldEntryTemplate, stats));
        }

        return lines;
    }
}
