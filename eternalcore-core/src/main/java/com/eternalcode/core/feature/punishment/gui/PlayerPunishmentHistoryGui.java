package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.entity.Player;

import java.util.UUID;

@Service
public class PlayerPunishmentHistoryGui {

    private static final String PLAYER_PLACEHOLDER = "{PLAYER}";

    private final PunishmentHistoryRepository punishmentHistoryRepository;
    private final PunishmentSettings punishmentSettings;
    private final PunishmentHistoryGuiBuilder guiBuilder;

    @Inject
    PlayerPunishmentHistoryGui(
        PunishmentHistoryRepository punishmentHistoryRepository,
        PunishmentSettings punishmentSettings,
        PunishmentHistoryGuiBuilder guiBuilder
    ) {
        this.punishmentHistoryRepository = punishmentHistoryRepository;
        this.punishmentSettings = punishmentSettings;
        this.guiBuilder = guiBuilder;
    }

    public void open(Player viewer, UUID targetUuid, String targetName) {
        PunishmentGuiSettings gui = this.punishmentSettings.gui();
        PunishmentHistoryGuiSession session = new PunishmentHistoryGuiSession(
            new PlayerPunishmentHistoryPageSource(this.punishmentHistoryRepository, targetUuid),
            new PunishmentHistoryGuiLayout(gui.contentRows()),
            gui.pagesPerFetch(),
            PunishmentHistoryFilter.ALL
        );

        this.guiBuilder.open(viewer, gui.playerTitle().replace(PLAYER_PLACEHOLDER, targetName), session);
    }
}
