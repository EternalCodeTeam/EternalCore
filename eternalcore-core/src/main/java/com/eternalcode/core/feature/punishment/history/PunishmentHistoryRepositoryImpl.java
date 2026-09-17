package com.eternalcode.core.feature.punishment.history;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.eternalcode.core.feature.punishment.history.PunishmentHistorySchema.*;

@Repository
class PunishmentHistoryRepositoryImpl implements PunishmentHistoryRepository {

    private final DSLContext dslContext;
    private final Scheduler scheduler;

    @Inject
    PunishmentHistoryRepositoryImpl(DSLContext dslContext, Scheduler scheduler) {
        this.dslContext = dslContext;
        this.scheduler = scheduler;

        this.createTable();
    }

    private void createTable() {
        this.dslContext.createTableIfNotExists(PUNISHMENT_HISTORY)
            .column(ID)
            .column(PUNISHMENT_ID)
            .column(TARGET_UUID)
            .column(TARGET_NAME)
            .column(OPERATOR_UUID)
            .column(OPERATOR_NAME)
            .column(ACTION)
            .column(REASON)
            .column(TIMESTAMP)
            .column(EXPIRES_AT)
            .constraints(DSL.constraint("pk_eternalcore_punishment_history").primaryKey(ID))
            .execute();
    }

    @Override
    public CompletableFuture<Void> save(PunishmentHistoryEntry entry) {
        Objects.requireNonNull(entry, "entry cannot be null");

        return this.scheduler.completeAsync(() -> {
            this.dslContext.insertInto(PUNISHMENT_HISTORY)
                .set(ID, entry.id().toString())
                .set(PUNISHMENT_ID, entry.punishmentId().toString())
                .set(TARGET_UUID, entry.target().uuid().toString())
                .set(TARGET_NAME, entry.target().name())
                .set(OPERATOR_UUID, entry.operator().uuid().toString())
                .set(OPERATOR_NAME, entry.operator().name())
                .set(ACTION, entry.action().name())
                .set(REASON, entry.reason())
                .set(TIMESTAMP, this.toOffsetDateTime(entry.timestamp()))
                .set(EXPIRES_AT, entry.expiresAt().map(this::toOffsetDateTime).orElse(null))
                .execute();
            return null;
        });
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> findByTarget(UUID targetUuid, int page, int pageSize) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");

        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PUNISHMENT_HISTORY)
            .where(TARGET_UUID.eq(targetUuid.toString()))
            .orderBy(TIMESTAMP.desc())
            .limit(pageSize)
            .offset(page * pageSize)
            .fetch(this::map));
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> findByOperator(UUID operatorUuid, int page, int pageSize) {
        Objects.requireNonNull(operatorUuid, "operatorUuid cannot be null");

        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PUNISHMENT_HISTORY)
            .where(OPERATOR_UUID.eq(operatorUuid.toString()))
            .orderBy(TIMESTAMP.desc())
            .limit(pageSize)
            .offset(page * pageSize)
            .fetch(this::map));
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> findRecent(int page, int pageSize) {
        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PUNISHMENT_HISTORY)
            .orderBy(TIMESTAMP.desc())
            .limit(pageSize)
            .offset(page * pageSize)
            .fetch(this::map));
    }

    private PunishmentHistoryEntry map(Record record) {
        PunishmentTarget target = new PunishmentTarget(
            UUID.fromString(record.get(TARGET_UUID)),
            record.get(TARGET_NAME)
        );

        PunishmentTarget operator = new PunishmentTarget(
            UUID.fromString(record.get(OPERATOR_UUID)),
            record.get(OPERATOR_NAME)
        );

        OffsetDateTime expiresAt = record.get(EXPIRES_AT);

        return new PunishmentHistoryEntry(
            UUID.fromString(record.get(ID)),
            UUID.fromString(record.get(PUNISHMENT_ID)),
            target,
            operator,
            PunishmentHistoryEntry.HistoryAction.valueOf(record.get(ACTION)),
            record.get(REASON),
            record.get(TIMESTAMP).toInstant(),
            expiresAt == null ? null : expiresAt.toInstant()
        );
    }

    private OffsetDateTime toOffsetDateTime(Instant instant) {
        return instant.atOffset(ZoneOffset.UTC);
    }
}
