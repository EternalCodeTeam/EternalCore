package com.eternalcode.core.test;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockServer {

    private final Server server;
    private final Map<UUID, Player> onlinePlayers = new HashMap<>();

    private final List<Consumer<PlayerJoinEvent>> joinListeners = new ArrayList<>();
    private final List<Consumer<PlayerQuitEvent>> quitListeners = new ArrayList<>();
    private final List<Consumer<PlayerKickEvent>> kickListeners = new ArrayList<>();

    public MockServer() {
        this.server = mock(Server.class);

        when(this.server.getPlayer(any(UUID.class))).thenReturn(null);
    }

    public Player joinPlayer(String name, UUID uuid) {
        Player player = mock(Player.class);

        when(player.getName()).thenReturn(name);
        when(player.getUniqueId()).thenReturn(uuid);
        when(this.server.getPlayer(eq(uuid))).then(invocation -> this.onlinePlayers.get(uuid));

        this.onlinePlayers.put(uuid, player);

        PlayerJoinEvent event = new PlayerJoinEvent(player, "Player " + name + " joined to the server!");

        for (Consumer<PlayerJoinEvent> joinListener : this.joinListeners) {
            joinListener.accept(event);
        }

        return player;
    }

    public void quitPlayer(Player player) {
        this.onlinePlayers.remove(player.getUniqueId());

        PlayerQuitEvent event = new PlayerQuitEvent(player, "Player " + player.getName() + " quit from the server!");

        for (Consumer<PlayerQuitEvent> quitListener : this.quitListeners) {
            quitListener.accept(event);
        }
    }

    public void kickPlayer(Player player) {
        PlayerKickEvent event = new PlayerKickEvent(player, "Player " + player.getName() + " has been kicked!", "Kicked!");

        for (Consumer<PlayerKickEvent> kickListener : this.kickListeners) {
            kickListener.accept(event);
        }

        this.quitPlayer(player);
    }

    public void listenJoin(Consumer<PlayerJoinEvent> joinListener) {
        this.joinListeners.add(joinListener);
    }

    public void listenQuit(Consumer<PlayerQuitEvent> quitListener) {
        this.quitListeners.add(quitListener);
    }

    public void listenKick(Consumer<PlayerKickEvent> kickListener) {
        this.kickListeners.add(kickListener);
    }

    public Server getServer() {
        return this.server;
    }
}
