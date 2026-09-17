package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;

import java.util.List;
import java.util.concurrent.CompletableFuture;

interface PunishmentHistoryPageSource {

    CompletableFuture<List<PunishmentHistoryEntry>> fetch(int page, int pageSize);
}
