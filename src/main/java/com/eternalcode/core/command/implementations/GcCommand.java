/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.utils.BenchmarkUtils;
import com.eternalcode.core.chat.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;


@FunnyComponent
public final class GcCommand {
    @FunnyCommand(
        name = "gc",
        aliases = {"garbagecollector", "tps", "mspt"},
        permission = "eternalcore.command.gc",
        usage = "&cPoprawne użycie &7/gc",
        acceptsExceeded = true
    )

    public void execute(Player player) {
        player.sendMessage(ChatUtils.color("&8[&e⭐&8]"));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8] &7TPS from last 1m, 5m, 15m: " + BenchmarkUtils.getTPS()));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8]"));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8] &7Max Memory: &f" + BenchmarkUtils.maxMemory() + " &7MB"));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8] &7Total memory: &f" + BenchmarkUtils.totalMemory() + " &7MB"));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8] &7Free memory: &f" + BenchmarkUtils.freeMemory() + " &7MB"));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8] &7Used memory: &f" + BenchmarkUtils.getUsedMemory() + " &7MB"));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8]"));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8] &7CPU Cores: &f" + BenchmarkUtils.cpuCores()));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8]"));
        player.sendMessage(ChatUtils.color("&8[&e⭐&8] &7Worlds info: "));
        List<World> worlds = Bukkit.getWorlds();
        for (World world : worlds) {
            int tileEntities = 0;
            for (Chunk chunk : world.getLoadedChunks()) {
                tileEntities += chunk.getTileEntities().length;
            }
            player.sendMessage(ChatUtils.color("&8[&e⭐&8] &8» &7" + world.getName() + "&f: " + world.getLoadedChunks().length + " &7chunks, &f" + world.getEntities().size() + " &7entities, &f" + tileEntities + " &7tilies"));
        }
        player.sendMessage(ChatUtils.color("&8[&e⭐&8]"));
    }
}
