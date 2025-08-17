package com.eternalcode.core.glowing;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import fr.skytasul.glowingentities.GlowingEntities;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Service
public class GlowingService {

    private final GlowingEntities glowingEntities;
    private final Logger logger;

    @Inject
    private GlowingService(Plugin plugin) {
        this.glowingEntities = new GlowingEntities(plugin);
        this.logger = plugin.getLogger();
    }

    public void enableGlowing(Entity entity, Player receiver, ChatColor color) {
        try {
            this.glowingEntities.setGlowing(entity, receiver, color);
        }
        catch (ReflectiveOperationException exception) {
            this.logger.log(Level.SEVERE, exception.getMessage(), exception);
        }
    }

    public void disableGlowing(Entity entity, Player receiver) {
        try {
            this.glowingEntities.unsetGlowing(entity, receiver);
        }
        catch (ReflectiveOperationException exception) {
            this.logger.log(Level.SEVERE, exception.getMessage(), exception);
        }
    }

    @Subscribe(EternalShutdownEvent.class)
    private void onDisable() {
        this.glowingEntities.disable();
    }
}
