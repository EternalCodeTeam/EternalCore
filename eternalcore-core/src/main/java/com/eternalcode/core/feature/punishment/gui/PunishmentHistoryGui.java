package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.entity.Player;

@Service
public class PunishmentHistoryGui {

    private final PunishmentHistoryService punishmentHistoryService;
    private final PunishmentSettings punishmentSettings;
    private final PunishmentHistoryGuiBuilder guiBuilder;

    @Inject
    PunishmentHistoryGui(
        PunishmentHistoryService punishmentHistoryService,
        PunishmentSettings punishmentSettings,
        PunishmentHistoryGuiBuilder guiBuilder
    ) {
        this.punishmentHistoryService = punishmentHistoryService;
        this.punishmentSettings = punishmentSettings;
        this.guiBuilder = guiBuilder;
    }

    public void open(Player viewer) {
        PunishmentHistoryPageSource pageSource = new RecentPunishmentHistoryPageSource(this.punishmentHistoryService);
        PunishmentHistoryGuiSession session = new PunishmentHistoryGuiSession(pageSource, this.punishmentSettings.historyGuiFetchBatchSize());

        this.guiBuilder.open(viewer, this.punishmentSettings.historyGuiTitle(), session);
    }
}
