package com.eternalcode.core;

import dev.rollczi.litegration.Litegration;
import dev.rollczi.litegration.client.Client;
import dev.rollczi.litegration.client.McProtocolLibClient;
import java.util.Objects;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class IntegrationBaseTest {

    private final static Plugin plugin = JavaPlugin.getProvidingPlugin(EternalCoreApi.class);
    private final static BukkitScheduler scheduler = Bukkit.getScheduler();


    protected static void joinPlayer(String name, Consumer<Player> action) {
        Litegration litegration = Litegration.getCurrent();
        scheduler.runTaskAsynchronously(plugin, () -> joinPlayer(name, litegration, action));

    }

    private static void joinPlayer(String name, Litegration litegration, Consumer<Player> action) {
        Client client = McProtocolLibClient.connected(name, litegration.getAddress(), litegration.getPort());
        Player rollczi = Objects.requireNonNull(Bukkit.getPlayer(client.getName()));
        scheduler.runTask(plugin, () -> {
            action.accept(rollczi);
            client.quit();
        });
    }

}
