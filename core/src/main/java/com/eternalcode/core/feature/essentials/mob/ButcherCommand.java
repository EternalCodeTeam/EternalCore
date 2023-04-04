package com.eternalcode.core.feature.essentials.mob;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.EntityUtil;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.amount.Min;
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

    @Execute
    void execute(Player player) {
        this.execute(player, 2);
    }

    @Execute(required = 1)
    void execute(Player player, @Arg int chunks) {
        this.execute(player, chunks, new MobEntity(MobType.ALL));
    }

    @Execute(required = 2)
    void execute(Player player, @Arg int chunks, @Arg MobEntity mobEntity) {
        MobType mobType = mobEntity.getMobType();

        if (mobType == MobType.UNDEFINED) {
            this.noticeService.create()
                .notice(translation -> translation.argument().noArgument())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.killMobs(player, chunks, (entity -> switch (mobType) {
            case PASSIVE -> EntityUtil.is(entity, Animals.class);
            case AGGRESSIVE -> EntityUtil.is(entity, Monster.class);
            case OTHER -> EntityUtil.is(entity, mobEntity.getEntityClass());
            case ALL -> EntityUtil.isMob(entity);

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