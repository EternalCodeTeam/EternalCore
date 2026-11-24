package com.eternalcode.core.feature.kit.buffer;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.kit.KitPlayerTaskQueue;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.inventory.ItemStack;

@Service
public class KitBufferService {

    private final KitPlayerTaskQueue queue = new KitPlayerTaskQueue();
    private final KitBufferRepository repository;

    @Inject
    KitBufferService(KitBufferRepository repository) {
        this.repository = repository;
    }

    public void add(UUID playerUniqueId, Collection<ItemStack> items) {
        if (items.isEmpty()) {
            return;
        }

        List<ItemStack> added = items.stream().map(ItemStack::clone).toList();

        this.queue.enqueue(playerUniqueId, () -> this.repository.find(playerUniqueId)
                .thenCompose(stored -> {
                    List<ItemStack> merged = new ArrayList<>(stored);
                    merged.addAll(added);
                    return this.repository.save(playerUniqueId, merged);
                }))
            .exceptionally(FutureHandler::handleException);
    }

    public CompletableFuture<List<ItemStack>> takeAll(UUID playerUniqueId) {
        return this.queue.enqueue(playerUniqueId, () -> this.repository.find(playerUniqueId)
            .thenCompose(stored -> {
                if (stored.isEmpty()) {
                    return CompletableFuture.completedFuture(stored);
                }

                return this.repository.delete(playerUniqueId).thenApply(deleted -> stored);
            }));
    }

    public CompletableFuture<Integer> countItems(UUID playerUniqueId) {
        return this.queue.enqueue(playerUniqueId, () -> this.repository.find(playerUniqueId))
            .thenApply(List::size);
    }
}
