package com.eternalcode.core.feature.fullserverbypass;


import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.commons.adventure.AdventureUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import panda.utilities.text.Joiner;

@PermissionDocs(
    name = "Bypass Full Server",
    description = "This feature allows you to bypass the full server, example for vip rank.",
    permission = FullServerBypassController.SLOT_BYPASS
)
@Controller
class FullServerBypassController implements Listener {

    static final String SLOT_BYPASS = "eternalcore.slot.bypass";

    private final TranslationManager translationManager;
    private final MiniMessage miniMessage;

    @Inject
    FullServerBypassController(TranslationManager translationManager, MiniMessage miniMessage) {
        this.translationManager = translationManager;
        this.miniMessage = miniMessage;
    }

    @EventHandler
    void onLogin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            Player player = event.getPlayer();

            if (player.hasPermission(SLOT_BYPASS)) {
                event.allow();

                return;
            }

            String serverFullMessage = this.getServerFullMessage();
            Component serverFullMessageComponent = this.miniMessage.deserialize(serverFullMessage);

            event.disallow(PlayerLoginEvent.Result.KICK_FULL, AdventureUtil.SECTION_SERIALIZER.serialize(serverFullMessageComponent));
        }
    }

    private String getServerFullMessage() {
        return Joiner.on("\n")
            .join(this.translationManager.getMessages().player().fullServerSlots())
            .toString();
    }
}
