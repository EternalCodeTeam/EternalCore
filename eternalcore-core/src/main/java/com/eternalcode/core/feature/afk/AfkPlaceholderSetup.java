package com.eternalcode.core.feature.afk;

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

@Controller
class AfkPlaceholderSetup {

    private final TranslationManager translationManager;

    @Inject
    AfkPlaceholderSetup(TranslationManager translationManager) {
        this.translationManager = translationManager;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry, AfkService afkService) {
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "afk",
            player -> String.valueOf(afkService.isAfk(player.getUniqueId()))));
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "afk_formatted",
            player -> {
                Translation messages = this.translationManager.getMessages(player.getUniqueId());
                return afkService.isAfk(player.getUniqueId()) ?
                    messages.afk().afkEnabledPlaceholder() : messages.afk().afkDisabledPlaceholder();
            }));

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
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
            }));
    }
}
