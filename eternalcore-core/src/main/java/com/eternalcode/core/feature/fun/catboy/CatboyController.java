package com.eternalcode.core.feature.fun.catboy;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.butcher.ButcherEntityRemoveEvent;
import com.eternalcode.core.feature.catboy.Catboy;
import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.teleport.event.EternalTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Duration;
import java.util.Optional;

@Controller
class CatboyController implements Listener {

    private static final Duration TICK = Duration.ofMillis(50L);

    private final CatboyService catboyService;
    private final CatBoyEntityService catBoyEntityService;
    private final Scheduler scheduler;
    private final Server server;

    @Inject
    CatboyController(
        CatboyService catboyService,
        CatBoyEntityService catBoyEntityService,
        Scheduler scheduler,
        Server server
    ) {
        this.catboyService = catboyService;
        this.catBoyEntityService = catBoyEntityService;
        this.scheduler = scheduler;
        this.server = server;
    }

    @EventHandler
    void onCatboyEvent(PlayerQuitEvent event) {
        this.catboyService.unmarkAsCatboy(event.getPlayer());
    }

    @EventHandler
    void onCatboyEvent(EntityDismountEvent event) {
        if (!(event.getDismounted() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof Cat cat)) {
            return;
        }

        if (this.catboyService.isCatboy(player.getUniqueId())) {
            cat.remove();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onTeleport(EternalTeleportEvent event) {
        Optional<Catboy> optionalCatboy = this.catboyService.getCatboy(event.getPlayer().getUniqueId());

        if (optionalCatboy.isPresent()) {
            Catboy catboy = optionalCatboy.get();

            this.catboyService.unmarkAsCatboy(event.getPlayer());
            this.scheduler.runLater(
                () -> this.catboyService.markAsCatboy(event.getPlayer(), catboy.selectedType()),
                TICK);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }

        World world = player.getWorld();

        if (this.catboyService.isCatboy(player.getUniqueId())) {
            world.playSound(player.getLocation(), Sound.ENTITY_CAT_PURREOW, 1.0F, 1.0F);
        }

        if (this.catboyService.isCatboy(damager.getUniqueId())) {
            world.playSound(damager.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1.0F, 1.0F);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onEntityRemoveFromWorld(ButcherEntityRemoveEvent event) {
        if (!(event.getEntity() instanceof Cat cat)) {
            return;
        }

        if (this.catBoyEntityService.isCatboy(cat)) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    void onServerShutdown(EternalShutdownEvent event) {
        for (Player onlinePlayer : this.server.getOnlinePlayers()) {
            boolean catboy = this.catboyService.isCatboy(onlinePlayer.getUniqueId());

            if (catboy) {
                this.catboyService.unmarkAsCatboy(onlinePlayer);
            }
        }
    }
}
