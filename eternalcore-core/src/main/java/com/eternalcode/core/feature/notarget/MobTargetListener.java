package com.eternalcode.core.feature.notarget;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

@Controller
public class MobTargetListener implements Listener {

    private final MobTargetService mobTargetService;

    @Inject
    public MobTargetListener(MobTargetService mobTargetService) {
        this.mobTargetService = mobTargetService;
    }

    @EventHandler
    public void onMobTarget(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player player && this.mobTargetService.doMobsIgnore(player.getUniqueId())) {
                event.setCancelled(true);
            }

    }
}