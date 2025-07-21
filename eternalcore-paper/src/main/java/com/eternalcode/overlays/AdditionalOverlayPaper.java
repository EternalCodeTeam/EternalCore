package com.eternalcode.overlays;

import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class AdditionalOverlayPaper {

    public static final AdditionalOverlayPaper ELDER_GUARDIAN = new AdditionalOverlayPaper(player -> player.showElderGuardian(false));
    public static final AdditionalOverlayPaper ELDER_GUARDIAN_SILENT = new AdditionalOverlayPaper(player -> player.showElderGuardian(true));

    private static final Environment ENVIRONMENT = PaperLib.getEnvironment();
    private static final Logger LOGGER = Logger.getLogger("AdditionalOverlayPaper");

    private final Consumer<Player> consumer;

    public AdditionalOverlayPaper(Consumer<Player> consumer) {
        this.consumer = consumer;
    }

    @SuppressWarnings("deprecation")
    public void show(Player player) {
        if (!ENVIRONMENT.isPaper()) {
            player.sendMessage(ChatColor.RED + "This feature is not supported on this server. Please contact the server administrator and check console!");
            LOGGER.warning("This feature is only available on paper, use Paper or its 1.17.x+ forks");

            return;
        }

        this.consumer.accept(player);
    }

}
