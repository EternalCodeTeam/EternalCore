package com.eternalcode.core.shared;

import java.util.Collection;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class OnlinePlayersRunnable implements Runnable {

    public static OnlinePlayersRunnable consuming(Consumer<Player> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("Consumer cannot be null");
        }

        return new OnlinePlayersRunnable() {
            @Override
            public void runFor(Player player) {
                consumer.accept(player);
            }
        };
    }

    public static Runnable asRunnable(Consumer<Player> consumer) {
        return consuming(consumer);
    }

    @Override
    public final void run() {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        if (onlinePlayers.isEmpty()) {
            return;
        }

        for (Player player : onlinePlayers) {
            if (player != null && player.isOnline()) {
                try {
                    runFor(player);
                }
                catch (Exception exception) {
                    handlePlayerException(player, exception);
                }
            }
        }
    }

    protected void handlePlayerException(Player player, Exception exception) {
        Bukkit.getLogger().warning(
            String.format(
                "Error processing player %s: %s",
                player.getName(), exception.getMessage())
        );
    }

    public abstract void runFor(Player player);
}
