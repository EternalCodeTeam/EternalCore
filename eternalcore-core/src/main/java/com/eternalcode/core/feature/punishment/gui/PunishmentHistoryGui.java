package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.database.PunishmentHistoryRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.entity.Player;

@Service
public class PunishmentHistoryGui {

    private final PunishmentHistoryRepository punishmentHistoryRepository;
    private final PunishmentSettings punishmentSettings;
    private final PunishmentHistoryGuiBuilder guiBuilder;

    @Inject
    PunishmentHistoryGui(
        PunishmentHistoryRepository punishmentHistoryRepository,
        PunishmentSettings punishmentSettings,
        PunishmentHistoryGuiBuilder guiBuilder
    ) {
        this.punishmentHistoryRepository = punishmentHistoryRepository;
        this.punishmentSettings = punishmentSettings;
        this.guiBuilder = guiBuilder;
    }

    public void open(Player viewer) {
        PunishmentGuiSettings gui = this.punishmentSettings.gui();
        PunishmentHistoryGuiSession session = new PunishmentHistoryGuiSession(
            new RecentPunishmentHistoryPageSource(this.punishmentHistoryRepository),
            new PunishmentHistoryGuiLayout(gui.contentRows()),
            gui.pagesPerFetch(),
            PunishmentHistoryFilter.ALL
        );

        this.guiBuilder.open(viewer, gui.recentTitle(), session);
    }
}
