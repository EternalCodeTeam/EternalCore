package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.punishment.kick.Kick;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Repository
class KickRepositoryOrmLite extends AbstractRepositoryOrmLite implements KickRepository {

    private final PunishmentMapper mapper;

    @Inject
    private KickRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler, PunishmentMapper mapper) throws SQLException {
        super(databaseManager, scheduler);
        this.mapper = mapper;

        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PunishmentTable.class);
    }

    @Override
    public CompletableFuture<Void> save(Kick kick) {
        return this.saveIfNotExist(PunishmentTable.class, this.mapper.kickToRow(kick)).thenApply(row -> null);
    }
}
