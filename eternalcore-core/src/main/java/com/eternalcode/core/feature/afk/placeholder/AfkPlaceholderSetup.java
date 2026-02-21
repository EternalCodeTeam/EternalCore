package com.eternalcode.core.feature.afk.placeholder;

import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs;
import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs.Entry;
import static com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs.Entry.*;
import com.eternalcode.core.feature.afk.Afk;
import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.DurationUtil;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.bukkit.Server;

@PlaceholdersDocs(
    category = "AFK",
    placeholders = {
        @Entry(
            name = "afk",
            description = "Returns `true` if the player is AFK, `false` otherwise",
            returnType = Type.BOOLEAN,
            requiresPlayer = true
        ),
        @Entry(
            name = "afk_formatted",
            description = "Returns a formatted AFK status message based on player's language settings e.g. `[AFK]` or ``",
            returnType = Type.STRING,
            requiresPlayer = true
        ),
        @Entry(
            name = "afk_time",
            description = "Returns the duration the player has been AFK in a formatted string e.g. `5m 30s`",
            returnType = Type.STRING,
            requiresPlayer = true
        ),
        @Entry(
            name = "afk_playercount",
            description = "Returns the total number of AFK players on the server",
            returnType = Type.INT,
            requiresPlayer = false
        )
    }
)
@Controller
class AfkPlaceholderSetup {

    private final TranslationManager translationManager;
    private final Server server;

    @Inject
    AfkPlaceholderSetup(TranslationManager translationManager, Server server) {
        this.translationManager = translationManager;
        this.server = server;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholders, AfkService afkService) {
        placeholders.register(Placeholder.ofBoolean("afk", player -> afkService.isAfk(player.getUniqueId())));
        placeholders.register(Placeholder.of("afk_formatted", player -> {
            Translation messages = this.translationManager.getMessages();
            return afkService.isAfk(player.getUniqueId()) ?
                messages.afk().afkEnabledPlaceholder() : messages.afk().afkDisabledPlaceholder();
        }));
        placeholders.register(Placeholder.of("afk_time", player -> {
            Optional<Afk> afkOptional = afkService.getAfk(player.getUniqueId());
            if (afkOptional.isEmpty()) {
                return "";
            }

            Afk afk = afkOptional.get();
            Instant start = afk.getStart();
            Instant now = Instant.now();
            Duration afkDuration = Duration.between(start, now);
            return DurationUtil.format(afkDuration, true);
        }));
        placeholders.register(Placeholder.ofLong("afk_playercount", player -> {
            long afkPlayerCount = this.server.getOnlinePlayers()
                .stream()
                .filter(onlinePlayer -> afkService.isAfk(onlinePlayer.getUniqueId()))
                .count();
            return afkPlayerCount;
        }));
    }
}
