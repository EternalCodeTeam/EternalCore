package com.eternalcode.core.feature.language;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Controller
class LanguageLoadController implements Listener {

    private final LanguageServiceImpl languageService;

    @Inject
    LanguageLoadController(LanguageServiceImpl languageService) {
        this.languageService = languageService;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onJoin(AsyncPlayerPreLoginEvent event) {
        this.languageService.loadLanguage(event.getUniqueId());
    }

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        this.languageService.unloadLanguage(event.getPlayer().getUniqueId());
    }

}
