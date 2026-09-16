package com.eternalcode.core.feature.punishment;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;

import org.jooq.DSLContext;
import org.jooq.Record;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jooq.impl.DSL;

import static com.eternalcode.core.feature.punishment.PunishmentSchema.*;

@Repository
class PunishmentRepositoryImpl implements PunishmentRepository {

    private final DSLContext dslContext;
    private final Scheduler scheduler;

    @Inject
    PunishmentRepositoryImpl(DSLContext dslContext, Scheduler scheduler) {
        this.dslContext = dslContext;
        this.scheduler = scheduler;

        this.createTable();
    }

    private void createTable() {
        this.dslContext.createTableIfNotExists(PUNISHMENTS)
            .column(ID)
            .column(TARGET_UUID)
            .column(TARGET_NAME)
            .column(OPERATOR_UUID)
            .column(OPERATOR_NAME)
            .column(TYPE)
            .column(REASON)
            .column(CREATED_AT)
            .column(EXPIRES_AT)
            .column(ACTIVE)
            .constraints(DSL.constraint("pk_eternalcore_punishments").primaryKey(ID))
            .execute();
    }

    @Override
    public CompletableFuture<Void> save(Punishment punishment) {
        Objects.requireNonNull(punishment, "punishment cannot be null");

        return this.scheduler.completeAsync(() -> {
            this.dslContext.insertInto(PUNISHMENTS)
                .set(ID, punishment.id().toString())
                .set(TARGET_UUID, punishment.target().uuid().toString())
                .set(TARGET_NAME, punishment.target().name())
                .set(OPERATOR_UUID, punishment.operator().uuid().toString())
                .set(OPERATOR_NAME, punishment.operator().name())
                .set(TYPE, punishment.type().name())
                .set(REASON, punishment.reason())
                .set(CREATED_AT, this.toOffsetDateTime(punishment.createdAt()))
                .set(EXPIRES_AT, punishment.expiresAt().map(this::toOffsetDateTime).orElse(null))
                .set(ACTIVE, punishment.active())
                .execute();
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> deactivate(UUID punishmentId) {
        Objects.requireNonNull(punishmentId, "punishmentId cannot be null");

        return this.scheduler.completeAsync(() -> {
            this.dslContext.update(PUNISHMENTS)
                .set(ACTIVE, false)
                .where(ID.eq(punishmentId.toString()))
                .execute();
            return null;
        });
    }

    @Override
    public CompletableFuture<Optional<Punishment>> findActive(UUID targetUuid, PunishmentType type) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");
        Objects.requireNonNull(type, "type cannot be null");

        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PUNISHMENTS)
            .where(TARGET_UUID.eq(targetUuid.toString()))
            .and(TYPE.eq(type.name()))
            .and(ACTIVE.eq(true))
            .fetchOptional(this::map));
    }

    @Override
    public CompletableFuture<List<Punishment>> findActive(UUID targetUuid) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");

        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PUNISHMENTS)
            .where(TARGET_UUID.eq(targetUuid.toString()))
            .and(ACTIVE.eq(true))
            .fetch(this::map));
    }

    @Override
    public CompletableFuture<List<Punishment>> findExpired(Instant now) {
        Objects.requireNonNull(now, "now cannot be null");

        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PUNISHMENTS)
            .where(ACTIVE.eq(true))
            .and(EXPIRES_AT.isNotNull())
            .and(EXPIRES_AT.le(this.toOffsetDateTime(now)))
            .fetch(this::map));
    }

    @Override
    public CompletableFuture<List<Punishment>> findAllActive(PunishmentType type) {
        Objects.requireNonNull(type, "type cannot be null");

        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PUNISHMENTS)
            .where(TYPE.eq(type.name()))
            .and(ACTIVE.eq(true))
            .fetch(this::map));
    }

    @Override
    public CompletableFuture<Integer> countByTargetAndType(UUID targetUuid, PunishmentType type) {
        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PUNISHMENTS)
            .where(TYPE.eq(type.name()))
            .and(TARGET_UUID.eq(targetUuid.toString()))
            .fetch(this::map)).thenApply(list -> list.size());
    }

    private Punishment map(Record record) {
        PunishmentTarget target = new PunishmentTarget(
            UUID.fromString(record.get(TARGET_UUID)),
            record.get(TARGET_NAME)
        );

        PunishmentTarget operator = new PunishmentTarget(
            UUID.fromString(record.get(OPERATOR_UUID)),
            record.get(OPERATOR_NAME)
        );

        OffsetDateTime expiresAt = record.get(EXPIRES_AT);

        return Punishment.builder()
            .id(UUID.fromString(record.get(ID)))
            .target(target)
            .operator(operator)
            .type(PunishmentType.valueOf(record.get(TYPE)))
            .reason(record.get(REASON))
            .createdAt(record.get(CREATED_AT).toInstant())
            .expiresAt(expiresAt == null ? null : expiresAt.toInstant())
            .active(record.get(ACTIVE))
            .build();
    }

    private OffsetDateTime toOffsetDateTime(Instant instant) {
        return instant.atOffset(ZoneOffset.UTC);
    }
}
