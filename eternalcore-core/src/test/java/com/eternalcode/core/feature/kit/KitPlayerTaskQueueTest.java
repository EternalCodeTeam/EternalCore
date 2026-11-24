package com.eternalcode.core.feature.kit;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.Test;

class KitPlayerTaskQueueTest {

    private final KitPlayerTaskQueue queue = new KitPlayerTaskQueue();
    private final UUID playerUniqueId = UUID.randomUUID();

    @Test
    void shouldRunTasksOfThePlayerInSubmissionOrder() {
        List<String> executed = new CopyOnWriteArrayList<>();
        CompletableFuture<Void> firstWrite = new CompletableFuture<>();

        CompletableFuture<Void> first = this.queue.enqueue(this.playerUniqueId, () -> {
            executed.add("first-start");
            return firstWrite.thenRun(() -> executed.add("first-end"));
        });
        CompletableFuture<Void> second = this.queue.enqueue(this.playerUniqueId, () -> {
            executed.add("second");
            return CompletableFuture.completedFuture(null);
        });

        assertThat(executed).containsExactly("first-start");
        assertThat(second).isNotDone();

        firstWrite.complete(null);

        assertThat(first).isCompleted();
        assertThat(second).isCompleted();
        assertThat(executed).containsExactly("first-start", "first-end", "second");
    }

    @Test
    void shouldContinueQueueAfterFailedTask() {
        CompletableFuture<Void> failed = this.queue.enqueue(this.playerUniqueId, () -> CompletableFuture.failedFuture(new IllegalStateException("database down")));
        CompletableFuture<String> next = this.queue.enqueue(this.playerUniqueId, () -> CompletableFuture.completedFuture("ok"));

        assertThat(failed).isCompletedExceptionally();
        assertThat(next).isCompletedWithValue("ok");
    }

    @Test
    void shouldNotBlockOtherPlayers() {
        CompletableFuture<Void> neverEnding = new CompletableFuture<>();
        this.queue.enqueue(this.playerUniqueId, () -> neverEnding);

        CompletableFuture<String> otherPlayer = this.queue.enqueue(UUID.randomUUID(), () -> CompletableFuture.completedFuture("ok"));

        assertThat(otherPlayer).isCompletedWithValue("ok");
    }

    @Test
    void shouldWaitForPendingTasksUpToTimeout() {
        CompletableFuture<Void> slowWrite = new CompletableFuture<>();
        this.queue.enqueue(this.playerUniqueId, () -> slowWrite);

        CompletableFuture.runAsync(() -> {
            sleep(Duration.ofMillis(100));
            slowWrite.complete(null);
        });

        long start = System.nanoTime();
        this.queue.awaitPending(this.playerUniqueId, Duration.ofSeconds(2));

        assertThat(slowWrite).isCompleted();
        assertThat(Duration.ofNanos(System.nanoTime() - start)).isLessThan(Duration.ofSeconds(2));
    }

    @Test
    void shouldStopWaitingAfterTimeout() {
        this.queue.enqueue(this.playerUniqueId, CompletableFuture::new);

        long start = System.nanoTime();
        this.queue.awaitPending(this.playerUniqueId, Duration.ofMillis(100));

        assertThat(Duration.ofNanos(System.nanoTime() - start)).isLessThan(Duration.ofSeconds(1));
    }

    private static void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
