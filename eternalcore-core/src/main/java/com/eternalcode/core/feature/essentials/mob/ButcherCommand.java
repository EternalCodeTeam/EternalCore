package com.eternalcode.core.feature.essentials.mob;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("SameParameterValue")
@Route(name = "butcher", aliases = { "killmob" })
@Permission("eternalcore.butcher")
public class ButcherCommand {

    private final NoticeService noticeService;
    private final int safeChunksNumber;

    public ButcherCommand(NoticeService noticeService, PluginConfiguration pluginConfiguration) {
        this.noticeService = noticeService;
        this.safeChunksNumber = pluginConfiguration.butcher.safeChunkNumber;
    }

    @Execute(required = 0)
    @DescriptionDocs(description = "Kills all mobs in 2 chunks around you", arguments = "<chunks> <mobType>")
    void execute(Player player) {
        this.execute(player, 2);
    }

    @Execute(required = 1)
    @DescriptionDocs(description = "Kills all mobs in specified chunks around you", arguments = "<chunks>")
    void execute(Player player, @Arg int chunks) {
        this.execute(player, chunks, new MobEntity(MobType.ALL));
    }

    @Execute(required = 2)
    @DescriptionDocs(description = "Kills specified mob in specified chunks around you", arguments = "<chunks> <mobType>")
    void execute(Player player, @Arg int chunks, @Arg MobEntity mobEntity) {
        this.killMobs(player, chunks, mobEntity::isMatch);
    }

    private void killMobs(Player player, int chunksNumber, MobFilter mobFilter) {
        if (chunksNumber <= 0) {
            this.noticeService.create()
                .notice(translation -> translation.argument().incorrectNumberOfChunks())
                .player(player.getUniqueId())
                .send();

            return;
        }

        if (chunksNumber > this.safeChunksNumber) {
            this.noticeService.create()
                .notice(translation -> translation.player().safeChunksMessage())
                .player(player.getUniqueId())
                .placeholder("{SAFE_CHUNKS}", String.valueOf(this.safeChunksNumber))
                .send();

            return;
        }

        Collection<Chunk> chunks = this.getChunksNearPlayer(player, chunksNumber);

        int killedMobs = 0;

        for (Chunk chunk : chunks) {
            for (Entity entity : chunk.getEntities()) {

                if (!mobFilter.filterMob(entity)) {
                    continue;
                }

                entity.remove();
                killedMobs++;
            }
        }

        this.noticeService.create()
            .notice(translation -> translation.player().butcherCommand())
            .player(player.getUniqueId())
            .placeholder("{KILLED}", String.valueOf(killedMobs))
            .send();
    }

    private Collection<Chunk> getChunksNearPlayer(Player player, int chunks) {
        Location location = player.getLocation();
        World world = player.getWorld();

        // bit shift
        int chunkX = location.getBlockX() >> 4; // equal to x / 16
        int chunkZ = location.getBlockZ() >> 4; // equal to z / 16

        List<Chunk> chunkList = new ArrayList<>();

        for (int x = chunkX - chunks; x <= chunkX + chunks; x++) {
            for (int z = chunkZ - chunks; z <= chunkZ + chunks; z++) {
                chunkList.add(world.getChunkAt(x, z));
            }
        }

        return chunkList;
    }

}
