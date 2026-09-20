package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.entity.Player;

@Service
class PunishmentHistoryGuiBuilder {

    void open(Player viewer, String titleTemplate, PunishmentHistoryGuiSession session) {
        session.loadNextBatch();
    }

    private void openWindow(Player viewer, String titleTemplate, PunishmentHistoryGuiSession session) {

    }
}
