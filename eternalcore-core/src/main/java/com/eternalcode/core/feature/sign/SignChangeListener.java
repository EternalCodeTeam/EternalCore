package com.eternalcode.core.feature.sign;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

@FeatureDocs(
    name = "Colored sign",
    description = "Allows you to use color codes in signs",
    permission = "eternalcore.sign"
)
@Controller
class SignChangeListener implements Listener {

    private final MiniMessage miniMessage;

    @Inject
    SignChangeListener(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    @EventHandler
    void onSign(SignChangeEvent event) {
        Player player = event.getPlayer();
        String[] lines = event.getLines();

       if (player.hasPermission("eternalcore.sign")) {
            for (int i = 0; i < lines.length; i++) {
                event.setLine(i, AdventureUtil.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(lines[i])));
            }
        }
    }
}
