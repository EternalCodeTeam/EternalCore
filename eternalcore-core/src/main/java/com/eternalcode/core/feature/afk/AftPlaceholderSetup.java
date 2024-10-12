package com.eternalcode.core.feature.afk;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerService;

@Controller
class AftPlaceholderSetup implements Subscriber {

    private final TranslationManager translationManager;
    private final ViewerService viewerService;

    @Inject
    AftPlaceholderSetup(TranslationManager translationManager, ViewerService viewerService) {
        this.translationManager = translationManager;
        this.viewerService = viewerService;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry, AfkService afkService) {
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "afk",
            player -> String.valueOf(afkService.isAfk(player.getUniqueId()))));
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "afk_formatted",
            player -> {
                Translation messages = this.translationManager.getMessages(this.viewerService.player(player.getUniqueId()));
                return afkService.isAfk(player.getUniqueId()) ?
                    messages.afk().afkEnabledPlaceholder() : messages.afk().afkDisabledPlaceholder();
            }));
    }
}
