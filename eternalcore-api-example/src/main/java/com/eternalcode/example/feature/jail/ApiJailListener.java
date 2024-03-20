package com.eternalcode.example.feature.jail;

import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ApiJailListener implements Listener {

    private final Server server;

    public ApiJailListener(Server server) {
        this.server = server;
    }

    /**
     * This method applies blindness effect to the player when they are jailed.
     **/
    @EventHandler
    public void onJailDetainEvent(JailDetainEvent event) {
        Player player = event.getPlayer();

        PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 2);

        player.addPotionEffect(effect);
    }

    /**
     * This method removes blindness effect from the player when they are released from jail.
     **/
    @EventHandler
    public void onJailReleaseEvent(JailReleaseEvent event) {
        Player player = this.server.getPlayer(event.getPlayerUniqueId());

        assert player != null;
        player.removePotionEffect(PotionEffectType.BLINDNESS);
    }
}
