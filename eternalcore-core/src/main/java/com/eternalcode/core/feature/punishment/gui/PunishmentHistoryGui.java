package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.database.PunishmentRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.entity.Player;

@Service
public class PunishmentHistoryGui {

    private final PunishmentRepository punishmentRepository;
    private final PunishmentSettings punishmentSettings;
    private final PunishmentHistoryGuiBuilder guiBuilder;

    @Inject
    PunishmentHistoryGui(
        PunishmentRepository punishmentRepository,
        PunishmentSettings punishmentSettings,
        PunishmentHistoryGuiBuilder guiBuilder
    ) {
        this.punishmentRepository = punishmentRepository;
        this.punishmentSettings = punishmentSettings;
        this.guiBuilder = guiBuilder;
    }

    public void open(Player viewer) {
        PunishmentGuiSettings gui = this.punishmentSettings.gui();
        PunishmentHistoryGuiSession session = new PunishmentHistoryGuiSession(
            new RecentPunishmentHistoryPageSource(this.punishmentRepository),
            new PunishmentHistoryGuiLayout(gui.contentRows()),
            gui.pagesPerFetch()
        );

        this.guiBuilder.open(viewer, gui.recentTitle(), session);
    }
}
