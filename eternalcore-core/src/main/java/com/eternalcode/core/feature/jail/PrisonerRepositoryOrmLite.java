package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
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
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PrisonerTable.class);
    }

    @Override
    public CompletableFuture<Optional<JailedPlayer>> getPrisoner(UUID uuid) {
        return this.selectSafe(PrisonerTable.class, uuid)
            .thenApply(optional -> optional.map(PrisonerTable::toPrisoner));
    }

    @Override
    public CompletableFuture<Set<JailedPlayer>> getPrisoners() {
        return this.selectAll(PrisonerTable.class)
            .thenApply(prisonerTables -> prisonerTables.stream()
                .map(PrisonerTable::toPrisoner)
                .collect(Collectors.toSet()));
    }

    @Override
    public void deletePrisoner(UUID uuid) {
        this.deleteById(PrisonerTable.class, uuid);
    }

    @Override
    public void savePrisoner(JailedPlayer jailedPlayer) {
        this.save(PrisonerTable.class, PrisonerTable.from(jailedPlayer));
    }

    @Override
    public void deleteAllPrisoners() {
        this.delete(PrisonerTable.class, new PrisonerTable());
    }
}
