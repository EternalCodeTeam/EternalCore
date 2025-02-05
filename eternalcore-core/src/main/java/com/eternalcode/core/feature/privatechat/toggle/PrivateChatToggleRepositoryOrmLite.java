package com.eternalcode.core.feature.privatechat.toggle;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
class PrivateChatToggleRepositoryOrmLite extends AbstractRepositoryOrmLite implements PrivateChatToggleRepository {

    @Inject
    private PrivateChatToggleRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PrivateChatToggleWrapper.class);
    }

    @Override
    public CompletableFuture<PrivateChatToggleState> getPrivateChatToggleState(UUID uuid) {
        return this.selectSafe(PrivateChatToggleWrapper.class, uuid)
            .thenApply(
                optional -> optional.map(PrivateChatToggleWrapper::isEnabled).orElse(PrivateChatToggleState.ENABLED)
            );
    }

    @Override
    public CompletableFuture<Void> setPrivateChatToggle(UUID uuid, PrivateChatToggleState toggledOff) {
        return this.save(PrivateChatToggleWrapper.class, new PrivateChatToggleWrapper(uuid, toggledOff))
            .thenApply(status -> null);
    }
}
