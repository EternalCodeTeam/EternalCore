package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.entity.Player;

import java.util.UUID;

@Service
public class PlayerPunishmentHistoryGui {

    private final PunishmentHistoryService punishmentHistoryService;
    private final PunishmentSettings punishmentSettings;
    private final PunishmentHistoryGuiBuilder guiBuilder;

    @Inject
    PlayerPunishmentHistoryGui(
        PunishmentHistoryService punishmentHistoryService,
        PunishmentSettings punishmentSettings,
        PunishmentHistoryGuiBuilder guiBuilder
    ) {
        this.punishmentHistoryService = punishmentHistoryService;
        this.punishmentSettings = punishmentSettings;
        this.guiBuilder = guiBuilder;
    }

    public void open(Player viewer, UUID targetUuid, String targetName) {
        PunishmentHistoryPageSource pageSource = new PlayerPunishmentHistoryPageSource(this.punishmentHistoryService, targetUuid);
        PunishmentHistoryGuiSession session = new PunishmentHistoryGuiSession(pageSource, this.punishmentSettings.historyGuiFetchBatchSize());

        String title = this.punishmentSettings.historyGuiPlayerTitle().replace("{PLAYER}", targetName);

        this.guiBuilder.open(viewer, title, session);
    }
}
