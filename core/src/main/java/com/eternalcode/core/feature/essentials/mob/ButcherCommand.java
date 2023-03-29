package com.eternalcode.core.feature.essentials.mob;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.async.Async;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Animals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Route(name = "butcher", aliases = { "killmob" })
@Permission("eternalcore.butcher")
public class ButcherCommand {

    private final NoticeService noticeService;
    private final int safeChunksNumber;

    public ButcherCommand(NoticeService noticeService, PluginConfiguration pluginConfiguration) {
        this.noticeService = noticeService;
        this.safeChunksNumber = pluginConfiguration.butcher.safeChunkNumber;
    }

    @Async
    @Execute(required = 0)
    void execute(Player player) {
        this.execute(player, 2, MobType.ALL);
    }

    @Execute(required = 2)
    @SuppressWarnings("SameParameterValue")
    void execute(Player player, @Arg int chunks, @Arg MobType mobType) {

        if (mobType == MobType.UNDEFINED) {
            this.noticeService.create()
                .notice(translation -> translation.argument().noArgument())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.killMobs(player, chunks, (entity -> switch (mobType) {
            case PASSIVE -> MobType.is(entity, Animals.class);
            case AGGRESSIVE -> MobType.is(entity, Monster.class);
            case OTHER -> MobType.is(entity, mobType.getMobClass());
            case ALL -> MobType.isMob(entity);

            default -> throw new IllegalStateException("MobType cannot be UNDEFINED!");
        }));
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
        }

        Collection<Chunk> chunks = this.getChunksNearPlayer(player, chunksNumber);
        AtomicInteger killedMobs = new AtomicInteger();

        for (Chunk chunk : chunks) {
            for (Entity entity : chunk.getEntities()) {

                if (!mobFilter.filterMob(entity)) {
                    continue;
                }

                entity.remove();
                killedMobs.incrementAndGet();
            }
        }

        this.noticeService.create()
            .notice(translation -> translation.player().butcherCommad())
            .player(player.getUniqueId())
            .placeholder("{KILLED}", String.valueOf(killedMobs.get()))
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
