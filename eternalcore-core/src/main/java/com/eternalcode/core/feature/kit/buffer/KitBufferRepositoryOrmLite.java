package com.eternalcode.core.feature.kit.buffer;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.kit.KitItemSerializer;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.inventory.ItemStack;

@Repository
class KitBufferRepositoryOrmLite extends AbstractRepositoryOrmLite implements KitBufferRepository {

    private final KitItemSerializer itemSerializer;

    @Inject
    private KitBufferRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler, KitItemSerializer itemSerializer) throws SQLException {
        super(databaseManager, scheduler);
        this.itemSerializer = itemSerializer;
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), KitBufferTable.class);
    }

    @Override
    public CompletableFuture<List<ItemStack>> find(UUID playerUniqueId) {
        return this.selectSafe(KitBufferTable.class, playerUniqueId)
            .thenApply(table -> table.map(found -> this.itemSerializer.deserialize(found.getItems())).orElse(List.of()));
    }

    @Override
    public CompletableFuture<Void> save(UUID playerUniqueId, List<ItemStack> items) {
        return this.save(KitBufferTable.class, new KitBufferTable(playerUniqueId, this.itemSerializer.serialize(items)))
            .thenApply(status -> null);
    }

    @Override
    public CompletableFuture<Void> delete(UUID playerUniqueId) {
        return this.deleteById(KitBufferTable.class, playerUniqueId)
            .thenApply(deleted -> null);
    }
}
