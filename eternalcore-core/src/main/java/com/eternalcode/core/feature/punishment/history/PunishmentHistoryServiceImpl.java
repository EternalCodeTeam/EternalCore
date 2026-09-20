package com.eternalcode.core.feature.punishment.history;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.Server;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
class PunishmentHistoryServiceImpl implements PunishmentHistoryService {

    private static final int MAX_PAGE_SIZE = 150;

    private final PunishmentHistoryRepository punishmentHistoryRepository;
    private final Server server;

    @Inject
    PunishmentHistoryServiceImpl(PunishmentHistoryRepository punishmentHistoryRepository, Server server) {
        this.punishmentHistoryRepository = punishmentHistoryRepository;
        this.server = server;
    }

    @Override
    public void record(PunishmentHistoryEntry entry) {
        this.assertNotPrimaryThread();

        this.punishmentHistoryRepository.save(entry).join();
    }

    @Override
    public List<PunishmentHistoryEntry> findByTarget(UUID targetUuid, int page, int pageSize) {
        this.assertNotPrimaryThread();
        this.validatePagination(page, pageSize);

        return this.punishmentHistoryRepository.findByTarget(targetUuid, page, pageSize).join();
    }

    @Override
    public List<PunishmentHistoryEntry> findRecent(int page, int pageSize) {
        this.assertNotPrimaryThread();
        this.validatePagination(page, pageSize);

        return this.punishmentHistoryRepository.findRecent(page, pageSize).join();
    }

    private void validatePagination(int page, int pageSize) {
        if (page < 0) {
            throw new IllegalArgumentException("page cannot be negative");
        }
        if (pageSize <= 0 || pageSize > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("pageSize must be between 1 and " + MAX_PAGE_SIZE + " historyGuiFetchBatchSize=" + MAX_PAGE_SIZE);
        }
    }

    private void assertNotPrimaryThread() {
        if (this.server.isPrimaryThread()) {
            throw new IllegalStateException("PunishmentHistoryService must not be called from the main thread");
        }
    }
}
