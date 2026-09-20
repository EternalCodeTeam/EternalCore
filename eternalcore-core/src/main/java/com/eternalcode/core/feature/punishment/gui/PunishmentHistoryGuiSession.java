package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

final class PunishmentHistoryGuiSession {

    private final PunishmentHistoryPageSource pageSource;
    private final int fetchBatchSize;
    private final List<PunishmentHistoryEntry> loadedEntries = new ArrayList<>();

    private int nextDatabasePage = 0;
    private boolean hasMore = true;

    PunishmentHistoryGuiSession(PunishmentHistoryPageSource pageSource, int fetchBatchSize) {
        this.pageSource = pageSource;

        if (fetchBatchSize <= 0) {
            throw new IllegalArgumentException("fetchBatchSize must be positive");
        }

        this.fetchBatchSize = fetchBatchSize;
    }

    List<PunishmentHistoryEntry> loadedEntries() {
        return Collections.unmodifiableList(this.loadedEntries);
    }

    boolean hasMore() {
        return this.hasMore;
    }

    void loadNextBatch() {
        if (!this.hasMore) {
            return;
        }

        List<PunishmentHistoryEntry> entries = this.pageSource.fetch(this.nextDatabasePage, this.fetchBatchSize);

        this.loadedEntries.addAll(entries);
        this.nextDatabasePage++;
        this.hasMore = entries.size() == this.fetchBatchSize;
    }
}
