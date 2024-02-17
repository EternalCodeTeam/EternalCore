package com.eternalcode.core.feature.afk;

import com.eternalcode.core.bridge.adventure.legacy.Legacy;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.afk.event.AfkSwitchEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.viewer.Viewer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@Controller
class AfkController implements Listener {

    private final AfkService afkService;
    private final MiniMessage miniMessage;
    private final UserManager userManager;
    private final TranslationManager translationManager;
    private final PluginConfiguration config;

    @Inject
    AfkController(AfkService afkService, MiniMessage miniMessage, UserManager userManager, TranslationManager translationManager, PluginConfiguration config) {
        this.afkService = afkService;
        this.miniMessage = miniMessage;
        this.userManager = userManager;
        this.translationManager = translationManager;
        this.config = config;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onAfkSwitch(AfkSwitchEvent event) {
        UUID playerUUID = event.getAfk().getPlayer();

        if (this.afkService.isAfk(playerUUID) && this.config.afk.kickOnAfk) {
            Player player = Bukkit.getPlayer(playerUUID);

            if (player == null) {
                return;
            }

            User user = this.userManager.getOrCreate(playerUUID, player.getName());
            Translation translation = this.translationManager.getMessages(user);

            Component component = this.miniMessage.deserialize(translation.afk().afkKickReason());
            player.kickPlayer(Legacy.SECTION_SERIALIZER.serialize(component));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onMove(PlayerMoveEvent event) {
        UUID uniqueId = event.getPlayer().getUniqueId();

        this.afkService.markInteraction(uniqueId);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onQuit(PlayerQuitEvent event) {
        this.afkService.clearAfk(event.getPlayer().getUniqueId());
    }

}
