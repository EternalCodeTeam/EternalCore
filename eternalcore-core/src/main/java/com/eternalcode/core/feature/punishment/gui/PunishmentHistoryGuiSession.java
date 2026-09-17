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
    private boolean loading = false;

    PunishmentHistoryGuiSession(PunishmentHistoryPageSource pageSource, int fetchBatchSize) {
        this.pageSource = Objects.requireNonNull(pageSource, "pageSource cannot be null");

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

    CompletableFuture<Void> loadNextBatch() {
        if (this.loading || !this.hasMore) {
            return CompletableFuture.completedFuture(null);
        }

        this.loading = true;

        return this.pageSource.fetch(this.nextDatabasePage, this.fetchBatchSize)
            .thenAccept(entries -> {
                this.loadedEntries.addAll(entries);
                this.nextDatabasePage++;
                this.hasMore = entries.size() == this.fetchBatchSize;
                this.loading = false;
            });
    }
}
