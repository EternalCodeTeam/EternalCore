package com.eternalcode.core.feature.catboy;

import com.eternalcode.commons.shared.scheduler.Scheduler;
import com.eternalcode.core.feature.teleport.event.EternalTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.time.Duration;
import java.util.Optional;

@Controller
class CatboyController implements Listener {

    private static final Duration TICK = Duration.ofMillis(50L);

    private final CatboyService catboyService;
    private final Scheduler scheduler;

    @Inject
    CatboyController(CatboyService catboyService, Scheduler scheduler) {
        this.catboyService = catboyService;
        this.scheduler = scheduler;
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
            this.scheduler.laterSync(() -> this.catboyService.markAsCatboy(event.getPlayer(), catboy.selectedType()), TICK);
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

}
