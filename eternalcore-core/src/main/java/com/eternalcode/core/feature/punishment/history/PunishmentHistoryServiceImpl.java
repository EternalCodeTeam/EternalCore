package com.eternalcode.core.feature.punishment.history;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
class PunishmentHistoryServiceImpl implements PunishmentHistoryService {

    private static final int MAX_PAGE_SIZE = 150;

    private final PunishmentHistoryRepository punishmentHistoryRepository;

    @Inject
    PunishmentHistoryServiceImpl(PunishmentHistoryRepository punishmentHistoryRepository) {
        this.punishmentHistoryRepository = punishmentHistoryRepository;
    }

    @Override
    public CompletableFuture<Void> record(PunishmentHistoryEntry entry) {
        Objects.requireNonNull(entry, "entry cannot be null");

        return this.punishmentHistoryRepository.save(entry);
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> findByTarget(UUID targetUuid, int page, int pageSize) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");
        this.validatePagination(page, pageSize);

        return this.punishmentHistoryRepository.findByTarget(targetUuid, page, pageSize);
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> findByOperator(UUID operatorUuid, int page, int pageSize) {
        Objects.requireNonNull(operatorUuid, "operatorUuid cannot be null");
        this.validatePagination(page, pageSize);

        return this.punishmentHistoryRepository.findByOperator(operatorUuid, page, pageSize);
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> findRecent(int page, int pageSize) {
        this.validatePagination(page, pageSize);

        return this.punishmentHistoryRepository.findRecent(page, pageSize);
    }

    private void validatePagination(int page, int pageSize) {
        if (page < 0) {
            throw new IllegalArgumentException("page cannot be negative");
        }
        if (pageSize <= 0 || pageSize > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("pageSize must be between 1 and " + MAX_PAGE_SIZE + " historyGuiFetchBatchSize=" + MAX_PAGE_SIZE);
        }
    }
}
