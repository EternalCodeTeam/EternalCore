package com.eternalcode.core.feature.mobignore;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

@Controller
public class MobTargetController implements Listener {

    private final MobIgnoreService mobIgnoreService;

    @Inject
    public MobTargetController(MobIgnoreService mobIgnoreService) {
        this.mobIgnoreService = mobIgnoreService;
    }

    @EventHandler
    public void onMobTarget(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player player && this.mobIgnoreService.isIgnored(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
