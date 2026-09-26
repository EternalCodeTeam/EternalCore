package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.database.PunishmentRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.entity.Player;

import java.util.UUID;

@Service
public class PlayerPunishmentHistoryGui {

    private static final String PLAYER_PLACEHOLDER = "{PLAYER}";

    private final PunishmentRepository punishmentRepository;
    private final PunishmentSettings punishmentSettings;
    private final PunishmentHistoryGuiBuilder guiBuilder;

    @Inject
    PlayerPunishmentHistoryGui(
        PunishmentRepository punishmentRepository,
        PunishmentSettings punishmentSettings,
        PunishmentHistoryGuiBuilder guiBuilder
    ) {
        this.punishmentRepository = punishmentRepository;
        this.punishmentSettings = punishmentSettings;
        this.guiBuilder = guiBuilder;
    }

    public void open(Player viewer, UUID targetUuid, String targetName) {
        PunishmentGuiSettings gui = this.punishmentSettings.gui();
        PunishmentHistoryGuiSession session = new PunishmentHistoryGuiSession(
            new PlayerPunishmentHistoryPageSource(this.punishmentRepository, targetUuid),
            new PunishmentHistoryGuiLayout(gui.contentRows()),
            gui.pagesPerFetch()
        );

        this.guiBuilder.open(viewer, gui.playerTitle().replace(PLAYER_PLACEHOLDER, targetName), session);
    }
}
