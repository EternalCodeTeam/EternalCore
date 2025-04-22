package com.eternalcode.core.feature.backpack.repository;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.feature.backpack.Backpack;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.json.simple.JSONObject;

@Repository
public class BackpackRepositoryOrmLite extends AbstractRepositoryOrmLite implements BackpackRepository {

    private static final String OWNER_COLUMN = "owner";
    private static final String SLOT_COLUMN = "slot";


    @Inject
    public BackpackRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), BackpackWrapper.class);
    }

    @Override
    public CompletableFuture<Optional<Backpack>> getBackpack(UUID ownerUniqueId) {
        return this.getBackpack(ownerUniqueId, 1);
    }

    @Override
    public CompletableFuture<Optional<Backpack>> getBackpack(UUID ownerUniqueId, int backpackSlot) {
        return this.action(BackpackWrapper.class, dao -> Optional.ofNullable(dao.queryBuilder()
            .where()
            .eq(OWNER_COLUMN, ownerUniqueId)
            .and()
            .eq(SLOT_COLUMN, backpackSlot)
            .queryForFirst()).map(BackpackWrapper::toBackpack)
        );
    }

    @Override
    public CompletableFuture<Backpack> saveBackpack(UUID ownerUniqueId, Backpack backpack) {
        return this.save(BackpackWrapper.class, BackpackWrapper.from(backpack)).thenApply(result -> null);
    }

    @Override
    public CompletableFuture<Integer> removeBackpacks(UUID ownerUniqueId) {
        return this.action(BackpackWrapper.class, dao -> {
            DeleteBuilder<BackpackWrapper, Object> builder = dao.deleteBuilder();
            builder.where()
                .eq(OWNER_COLUMN, ownerUniqueId);
            return builder.delete();
        });
    }

    @Override
    public CompletableFuture<Integer> removeBackpack(UUID ownerUniqueId, int backpackSlot) {
        return this.action(BackpackWrapper.class, dao -> {
            DeleteBuilder<BackpackWrapper, Object> builder = dao.deleteBuilder();
            builder.where()
                .eq(OWNER_COLUMN, ownerUniqueId)
                .and()
                .eq(SLOT_COLUMN, backpackSlot);
            return builder.delete();
        });
    }
}
