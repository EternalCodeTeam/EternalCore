package com.eternalcode.core.feature.msg.toggle;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
class MsgToggleRepositoryOrmLite extends AbstractRepositoryOrmLite implements MsgToggleRepository {

    @Inject
    private MsgToggleRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), MsgStateTable.class);
    }

    @Override
    public CompletableFuture<MsgState> getPrivateChatState(UUID uuid) {
        return this.selectSafe(MsgStateTable.class, uuid)
            .thenApply(
                optional -> optional.map(MsgStateTable::isEnabled).orElse(MsgState.ENABLED)
            ).exceptionally(throwable -> MsgState.ENABLED);
    }

    @Override
    public CompletableFuture<Void> setPrivateChatState(UUID uuid, MsgState state) {
        return this.save(MsgStateTable.class, new MsgStateTable(uuid, state))
            .thenApply(status -> null);
    }
}
