package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.entity.Player;

@Service
public class PunishmentHistoryGui {

    private final PunishmentHistoryRepository punishmentHistoryRepository;
    private final PunishmentSettings punishmentSettings;
    private final PunishmentHistoryGuiBuilder guiBuilder;
    private final Scheduler scheduler;

    @Inject
    PunishmentHistoryGui(
        PunishmentHistoryRepository punishmentHistoryRepository,
        PunishmentSettings punishmentSettings,
        PunishmentHistoryGuiBuilder guiBuilder,
        Scheduler scheduler
    ) {
        this.punishmentHistoryRepository = punishmentHistoryRepository;
        this.punishmentSettings = punishmentSettings;
        this.guiBuilder = guiBuilder;
        this.scheduler = scheduler;
    }

    public void open(Player viewer) {
        PunishmentGuiSettings gui = this.punishmentSettings.gui();
        PunishmentHistoryGuiSession session = new PunishmentHistoryGuiSession(
            new RecentPunishmentHistoryPageSource(this.punishmentHistoryRepository, this.scheduler),
            new PunishmentHistoryGuiLayout(gui.contentRows()),
            gui.pagesPerFetch(),
            PunishmentHistoryFilter.ALL
        );

        this.guiBuilder.open(viewer, gui.recentTitle(), session);
    }
}
