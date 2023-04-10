package com.eternalcode.core.listener.player;

import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.util.legacy.Legacy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import panda.std.Option;
import panda.utilities.text.Joiner;

public class PlayerLoginListener implements Listener {

    private final TranslationManager translationManager;
    private final UserManager userManager;
    private final MiniMessage miniMessage;

    public PlayerLoginListener(TranslationManager translationManager, UserManager userManager, MiniMessage miniMessage) {
        this.translationManager = translationManager;
        this.userManager = userManager;
        this.miniMessage = miniMessage;
    }

    @EventHandler
    void onLogin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            Player player = event.getPlayer();

            if (player.hasPermission("eternalcore.slot.bypass")) {
                event.allow();

                return;
            }

            String serverFullMessage = this.extractServerFullMessage(player);
            Component serverFullMessageComponent = this.miniMessage.deserialize(serverFullMessage);

            event.disallow(PlayerLoginEvent.Result.KICK_FULL, Legacy.SECTION_SERIALIZER.serialize(serverFullMessageComponent));
        }
    }

    private String extractServerFullMessage(Player player) {
        Option<User> userOption = this.userManager.getUser(player.getUniqueId());

        if (userOption.isEmpty()) {
            return Joiner.on("\n")
                .join(this.translationManager.getDefaultMessages().player().fullServerSlots())
                .toString();
        }

        User user = userOption.get();

        return Joiner.on("\n")
            .join(this.translationManager.getMessages(user).player().fullServerSlots())
            .toString();
    }
}
