package com.eternalcode.core.feature.warp.inventory;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

@Repository
class WarpInventoryRepositoryImpl implements WarpInventoryRepository {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final WarpInventoryConfig warpInventoryConfig;
    private final ConfigurationManager configurationManager;
    private final Scheduler scheduler;
    private final Logger logger;

    @Inject
    public WarpInventoryRepositoryImpl(
        ConfigurationManager configurationManager,
        WarpInventoryConfig warpInventoryConfig,
        Scheduler scheduler,
        Logger logger
    ) {
        this.configurationManager = configurationManager;
        this.warpInventoryConfig = warpInventoryConfig;
        this.scheduler = scheduler;
        this.logger = logger;
    }

    @Override
    public CompletableFuture<Void> saveWarpInventoryItem(String warpName, WarpInventoryItem item) {
        if (warpName == null || warpName.trim().isEmpty()) {
            return CompletableFuture.failedFuture(
                new IllegalArgumentException("Warp name cannot be null or empty")
            );
        }

        if (item == null) {
            return CompletableFuture.failedFuture(
                new IllegalArgumentException("WarpInventoryItem cannot be null")
            );
        }

        return this.transactionalRun(items -> items.put(warpName, item));
    }

    @Override
    public CompletableFuture<Void> removeWarpInventoryItem(String warpName) {
        if (warpName == null || warpName.trim().isEmpty()) {
            return CompletableFuture.failedFuture(
                new IllegalArgumentException("Warp name cannot be null or empty")
            );
        }

        return this.transactionalRun(items -> items.remove(warpName));
    }

    @Override
    public CompletableFuture<WarpInventoryItem> getWarpInventoryItem(String warpName) {
        if (warpName == null || warpName.trim().isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }

        return this.transactionalSupply(items -> items.get(warpName));
    }

    @Override
    public CompletableFuture<Map<String, WarpInventoryItem>> getAllWarpInventoryItems() {
        return this.transactionalSupply(items -> new HashMap<>(items));
    }

    private CompletableFuture<Void> transactionalRun(Consumer<Map<String, WarpInventoryItem>> editor) {
        return this.transactionalSupply(items -> {
            editor.accept(items);
            return null;
        });
    }

    private <T> CompletableFuture<T> transactionalSupply(Function<Map<String, WarpInventoryItem>, T> editor) {
        return this.scheduler.completeAsync(() -> {
            this.readWriteLock.writeLock().lock();
            try {
                Map<String, WarpInventoryItem> items = new HashMap<>(this.warpInventoryConfig.getItems());
                T result = editor.apply(items);
                this.warpInventoryConfig.setItems(items);
                this.configurationManager.save(this.warpInventoryConfig);
                return result;
            }
            catch (Exception exception) {
                this.logger.severe("Error during transactional operation: " + exception.getMessage());
                throw new RuntimeException("Failed to perform transactional operation", exception);
            }
            finally {
                this.readWriteLock.writeLock().unlock();
            }
        });
    }
}
