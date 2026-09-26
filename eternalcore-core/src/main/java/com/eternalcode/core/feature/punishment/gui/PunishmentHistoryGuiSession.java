package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.Punishment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

final class PunishmentHistoryGuiSession {

    private final PunishmentHistoryPageSource pageSource;
    private final PunishmentHistoryGuiLayout layout;
    private final PunishmentHistoryFilter filter;
    private final int pagesPerFetch;
    private final int fetchBatchSize;
    private final List<Punishment> loadedPunishments = new ArrayList<>();

    private int nextBatch = 0;
    private boolean hasMore = true;
    private CompletableFuture<Void> pendingLoad = CompletableFuture.completedFuture(null);

    PunishmentHistoryGuiSession(
        PunishmentHistoryPageSource pageSource,
        PunishmentHistoryGuiLayout layout,
        int pagesPerFetch,
        PunishmentHistoryFilter filter
    ) {
        this.pageSource = pageSource;
        this.layout = layout;
        this.filter = filter;

        if (pagesPerFetch <= 0) {
            throw new IllegalArgumentException("pagesPerFetch must be positive, got " + pagesPerFetch);
        }

        this.pagesPerFetch = pagesPerFetch;
        this.fetchBatchSize = layout.pageSize() * pagesPerFetch;
    }

    PunishmentHistoryGuiLayout layout() {
        return this.layout;
    }

    PunishmentHistoryFilter filter() {
        return this.filter;
    }

    PunishmentHistoryGuiSession withFilter(PunishmentHistoryFilter filter) {
        return new PunishmentHistoryGuiSession(this.pageSource, this.layout, this.pagesPerFetch, filter);
    }

    synchronized CompletableFuture<Void> loadPage(int page) {
        this.requireValidPage(page);

        if (this.isLoaded(page) || !this.hasMore) {
            return CompletableFuture.completedFuture(null);
        }

        if (!this.pendingLoad.isDone()) {
            return this.pendingLoad;
        }

        this.pendingLoad = this.pageSource.fetch(this.filter.types(), this.nextBatch, this.fetchBatchSize)
            .thenAccept(this::append);
        return this.pendingLoad;
    }

    synchronized List<Punishment> page(int page) {
        this.requireValidPage(page);

        int from = page * this.layout.pageSize();
        if (from >= this.loadedPunishments.size()) {
            return List.of();
        }

        int to = Math.min(from + this.layout.pageSize(), this.loadedPunishments.size());
        return List.copyOf(this.loadedPunishments.subList(from, to));
    }

    synchronized int lastPage() {
        return Math.max(0, (this.loadedPunishments.size() - 1) / this.layout.pageSize());
    }

    synchronized boolean hasNextPage(int page) {
        return (page + 1) * this.layout.pageSize() < this.loadedPunishments.size() || this.hasMore;
    }

    private synchronized void append(List<Punishment> batch) {
        this.loadedPunishments.addAll(batch);
        this.nextBatch++;
        this.hasMore = batch.size() == this.fetchBatchSize;
    }

    private boolean isLoaded(int page) {
        return (page + 1) * this.layout.pageSize() <= this.loadedPunishments.size();
    }

    private void requireValidPage(int page) {
        if (page < 0) {
            throw new IllegalArgumentException("page cannot be negative, got " + page);
        }
    }
}
