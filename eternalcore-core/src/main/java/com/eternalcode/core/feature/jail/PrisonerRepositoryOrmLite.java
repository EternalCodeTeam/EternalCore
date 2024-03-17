package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;

import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
class PrisonerRepositoryOrmLite extends AbstractRepositoryOrmLite implements PrisonerRepository {

    @Inject
    private PrisonerRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PrisonerWrapper.class);
    }

    @Override
    public CompletableFuture<Optional<JailedPlayer>> getPrisoner(UUID uuid) {
        return this.selectSafe(PrisonerWrapper.class, uuid)
            .thenApply(optional -> optional.map(prisonerWrapper -> prisonerWrapper.toPrisoner()));
    }

    @Override
    public CompletableFuture<Set<JailedPlayer>> getPrisoners() {
        return this.selectAll(PrisonerWrapper.class)
            .thenApply(prisonerWrappers -> prisonerWrappers.stream()
                .map(PrisonerWrapper::toPrisoner)
                .collect(Collectors.toSet()));
    }

    @Override
    public void deletePrisoner(UUID uuid) {
        this.deleteById(PrisonerWrapper.class, uuid);
    }

    @Override
    public void editPrisoner(JailedPlayer jailedPlayer) {
        this.save(PrisonerWrapper.class, PrisonerWrapper.from(jailedPlayer));
    }

    @Override
    public void deleteAllPrisoners() {
        this.delete(PrisonerWrapper.class, new PrisonerWrapper());
    }

    @Override
    public CompletableFuture<List<JailedPlayer>> getAllPrisoners() {
        return this.selectAll(PrisonerWrapper.class)
            .thenApply(prisonerWrappers -> prisonerWrappers.stream()
                .map(PrisonerWrapper::toPrisoner)
                .toList());
    }

    @Override
    public void savePrisoner(JailedPlayer jailedPlayer) {
        this.save(PrisonerWrapper.class, PrisonerWrapper.from(jailedPlayer));
    }

}
