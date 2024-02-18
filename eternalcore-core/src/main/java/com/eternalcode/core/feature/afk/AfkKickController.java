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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

@Controller
class AfkKickController implements Listener {

    private final MiniMessage miniMessage;
    private final Server server;
    private final UserManager userManager;
    private final PluginConfiguration configuration;
    private final TranslationManager translationManager;

    @Inject
    public AfkKickController(
        MiniMessage miniMessage,
        Server server,
        UserManager userManager,
        PluginConfiguration configuration,
        TranslationManager translationManager
    ) {
        this.miniMessage = miniMessage;
        this.server = server;
        this.userManager = userManager;
        this.configuration = configuration;
        this.translationManager = translationManager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onAfkSwitch(AfkSwitchEvent event) {
        UUID playerUUID = event.getAfk().getPlayer();

        if (!event.isAfk() || !this.configuration.afk.kickOnAfk) {
            return;
        }

        Player player = this.server.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        User user = this.userManager.getOrCreate(playerUUID, player.getName());
        Translation translation = this.translationManager.getMessages(user);

        Component component = this.miniMessage.deserialize(translation.afk().afkKickReason());
        player.kickPlayer(Legacy.SECTION_SERIALIZER.serialize(component));
    }
}
