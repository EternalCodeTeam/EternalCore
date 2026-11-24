package com.eternalcode.core.feature.kit.buffer;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.multification.shared.Formatter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
class KitBufferController implements Listener {

    private final KitBufferService bufferService;
    private final KitNotices notices;
    private final KitSettings settings;

    @Inject
    KitBufferController(KitBufferService bufferService, KitNotices notices, KitSettings settings) {
        this.bufferService = bufferService;
        this.notices = notices;
        this.settings = settings;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onJoin(PlayerJoinEvent event) {
        if (!this.settings.notifyAboutBufferedItems()) {
            return;
        }

        Player player = event.getPlayer();

        this.bufferService.countItems(player.getUniqueId())
            .thenAccept(amount -> {
                if (amount > 0 && player.isOnline()) {
                    Formatter formatter = new Formatter()
                        .register(KitNotices.AMOUNT, amount);

                    this.notices.send(player, KitMessages::bufferWaiting, formatter);
                }
            })
            .exceptionally(FutureHandler::handleException);
    }
}
