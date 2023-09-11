package com.eternalcode.core.updater;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.CompletableFuture;

@FeatureDocs(
    name = "Updater",
    permission = "eternalcore.receiveupdates",
    description = "Sends a message to the player when a new plugin update is available after joining the server."
)
@Controller
class UpdaterController implements Listener {

    private static final String NEW_VERSION_AVAILABLE = "<b><gradient:#8a1212:#fc6b03>EternalCore:</gradient></b> <color:#fce303>New version of EternalCore is available, please update!";

    private final PluginConfiguration pluginConfiguration;
    private final UpdaterService updaterService;
    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    @Inject
    UpdaterController(PluginConfiguration pluginConfiguration, UpdaterService updaterService, AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.pluginConfiguration = pluginConfiguration;
        this.updaterService = updaterService;
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("eternalcore.receiveupdates")) {
            return;
        }

        if (!this.pluginConfiguration.shouldReceivePluginUpdates) {
            return;
        }

        Audience playerAudience = this.audienceProvider.player(player.getUniqueId());

        CompletableFuture<Boolean> isUpToDate = this.updaterService.isUpToDate();

        isUpToDate.whenComplete((isUpToUpdate, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                return;
            }

            if (!isUpToUpdate) {
                playerAudience.sendMessage(this.miniMessage.deserialize(NEW_VERSION_AVAILABLE));
            }
        });
    }

}
