package com.eternalcode.core.feature.afk.placeholder;

import com.eternalcode.annotations.scan.placeholder.PlaceholderDocs;
import com.eternalcode.core.feature.afk.Afk;
import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.DurationUtil;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.bukkit.Server;

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
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry, AfkService afkService) {
        placeholderRegistry.registerPlaceholder(this.createAfkPlaceholder(afkService));
        placeholderRegistry.registerPlaceholder(this.createAfkFormattedPlaceholder(afkService));
        placeholderRegistry.registerPlaceholder(this.createAfkTimePlaceholder(afkService));
        placeholderRegistry.registerPlaceholder(this.createAfkPlayerCountPlaceholder(afkService));
    }

    @PlaceholderDocs(
        name = "afk",
        description = "Returns true if the player is AFK, false otherwise",
        example = "true",
        returnType = "boolean",
        category = "AFK",
        requiresPlayer = true
    )
    private PlaceholderReplacer createAfkPlaceholder(AfkService afkService) {
        return PlaceholderReplacer.of(
            "afk",
            player -> String.valueOf(afkService.isAfk(player.getUniqueId()))
        );
    }

    @PlaceholderDocs(
        name = "afk_formatted",
        description = "Returns a formatted AFK status message based on player's language settings",
        example = "[AFK]",
        returnType = "String",
        category = "AFK",
        requiresPlayer = true
    )
    private PlaceholderReplacer createAfkFormattedPlaceholder(AfkService afkService) {
        return PlaceholderReplacer.of(
            "afk_formatted",
            player -> {
                Translation messages = this.translationManager.getMessages(player.getUniqueId());
                return afkService.isAfk(player.getUniqueId()) ?
                    messages.afk().afkEnabledPlaceholder() : messages.afk().afkDisabledPlaceholder();
            }
        );
    }

    @PlaceholderDocs(
        name = "afk_time",
        description = "Returns the duration the player has been AFK in a formatted string",
        example = "5m 30s",
        returnType = "String",
        category = "AFK",
        requiresPlayer = true
    )
    private PlaceholderReplacer createAfkTimePlaceholder(AfkService afkService) {
        return PlaceholderReplacer.of(
            "afk_time",
            player -> {
                Optional<Afk> afkOptional = afkService.getAfk(player.getUniqueId());
                if (afkOptional.isEmpty()) {
                    return "";
                }

                Afk afk = afkOptional.get();
                Instant start = afk.getStart();
                Instant now = Instant.now();
                Duration afkDuration = Duration.between(start, now);
                return DurationUtil.format(afkDuration, true);
            }
        );
    }

    @PlaceholderDocs(
        name = "afk_playercount",
        description = "Returns the total number of AFK players on the server",
        example = "3",
        returnType = "int",
        category = "AFK",
        requiresPlayer = false
    )
    private PlaceholderReplacer createAfkPlayerCountPlaceholder(AfkService afkService) {
        return PlaceholderReplacer.of(
            "afk_playercount",
            ignoredPlayer -> {
                long afkPlayerCount = this.server.getOnlinePlayers()
                    .stream()
                    .filter(onlinePlayer -> afkService.isAfk(onlinePlayer.getUniqueId()))
                    .count();
                return String.valueOf(afkPlayerCount);
            }
        );
    }
}
