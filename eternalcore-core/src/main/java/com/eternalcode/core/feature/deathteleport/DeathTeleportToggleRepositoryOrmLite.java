package com.eternalcode.core.feature.deathteleport;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
class DeathTeleportToggleRepositoryOrmLite extends AbstractRepositoryOrmLite implements DeathTeleportToggleRepository {

    @Inject
    private DeathTeleportToggleRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), DeathTeleportStateTable.class);
    }

    @Override
    public CompletableFuture<DeathTeleportState> getState(UUID uuid) {
        return this.selectSafe(DeathTeleportStateTable.class, uuid)
            .thenApply(optional -> optional.map(DeathTeleportStateTable::state).orElse(DeathTeleportState.ENABLED))
            .exceptionally(throwable -> DeathTeleportState.ENABLED);
    }

    @Override
    public CompletableFuture<Void> setState(UUID uuid, DeathTeleportState state) {
        return this.save(DeathTeleportStateTable.class, new DeathTeleportStateTable(uuid, state))
            .thenApply(status -> null);
    }
}
