package com.eternalcode.core.feature.afk;

import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection;

import com.eternalcode.core.feature.afk.event.AfkSwitchEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import java.util.Optional;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

@Controller
class AfkKickController implements Listener {

    private final MiniMessage miniMessage;
    private final Server server;
    private final UserManager userManager;
    private final AfkSettings afkSettings;
    private final TranslationManager translationManager;

    @Inject
    public AfkKickController(
        MiniMessage miniMessage,
        Server server,
        UserManager userManager,
        AfkSettings afkSettings,
        TranslationManager translationManager
    ) {
        this.miniMessage = miniMessage;
        this.server = server;
        this.userManager = userManager;
        this.afkSettings = afkSettings;
        this.translationManager = translationManager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onAfkSwitch(AfkSwitchEvent event) {
        UUID playerUUID = event.getAfk().getPlayer();

        if (!event.isAfk() || !this.afkSettings.kickOnAfk()) {
            return;
        }

        Player player = this.server.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        Optional<User> optionalUser = this.userManager.getUser(playerUUID);
        User user = optionalUser.get();
        Translation translation = this.translationManager.getMessages(user.getUniqueId());

        Component component = this.miniMessage.deserialize(translation.afk().afkKickReason());
        player.kickPlayer(legacySection().serialize(component));
    }
}
