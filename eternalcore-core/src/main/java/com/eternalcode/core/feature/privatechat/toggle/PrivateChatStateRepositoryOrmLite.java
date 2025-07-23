package com.eternalcode.core.feature.privatechat.toggle;

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
class PrivateChatStateRepositoryOrmLite extends AbstractRepositoryOrmLite implements PrivateChatStateRepository {

    @Inject
    private PrivateChatStateRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PrivateChatStateTable.class);
    }

    @Override
    public CompletableFuture<PrivateChatState> getPrivateChatState(UUID uuid) {
        return this.selectSafe(PrivateChatStateTable.class, uuid)
            .thenApply(
                optional -> optional.map(PrivateChatStateTable::isEnabled).orElse(PrivateChatState.ENABLE)
            ).exceptionally(throwable -> PrivateChatState.ENABLE);
    }

    @Override
    public CompletableFuture<Void> setPrivateChatState(UUID uuid, PrivateChatState state) {
        return this.save(PrivateChatStateTable.class, new PrivateChatStateTable(uuid, state))
            .thenApply(status -> null);
    }
}
