package com.eternalcode.core.feature.punishment.ip;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.ip.EncryptedValue;
import com.eternalcode.core.ip.IpCryptoService;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.eternalcode.core.feature.punishment.ip.IpPunishmentSchema.*;

@Repository
class IpPunishmentRepositoryJooqImpl implements IpPunishmentRepository {

    private final DSLContext dslContext;
    private final Scheduler scheduler;
    private final IpCryptoService ipCryptoService;

    @Inject
    IpPunishmentRepositoryJooqImpl(DSLContext dslContext, Scheduler scheduler, IpCryptoService ipCryptoService) {
        this.dslContext = dslContext;
        this.scheduler = scheduler;
        this.ipCryptoService = ipCryptoService;

        this.createTable();
    }

    private void createTable() {
        this.dslContext.createTableIfNotExists(PUNISHMENT_IPS)
            .column(ID)
            .column(IP_CIPHERTEXT)
            .column(IP_IV)
            .column(IP_HASH)
            .column(TARGET_UUID)
            .column(TARGET_NAME)
            .column(OPERATOR_UUID)
            .column(OPERATOR_NAME)
            .column(REASON)
            .column(CREATED_AT)
            .column(EXPIRES_AT)
            .column(REVOKED_AT)
            .constraints(DSL.constraint("pk_eternalcore_punishment_ips").primaryKey(ID))
            .execute();
    }

    @Override
    public CompletableFuture<Void> save(IpPunishment ipPunishment) {
        return this.scheduler.completeAsync(() -> {
            EncryptedValue encrypted = this.ipCryptoService.encrypt(ipPunishment.ip());
            String hash = this.ipCryptoService.hash(ipPunishment.ip());

            this.dslContext.insertInto(PUNISHMENT_IPS)
                .set(ID, ipPunishment.id().toString())
                .set(IP_CIPHERTEXT, Base64.getEncoder().encodeToString(encrypted.ciphertext()))
                .set(IP_IV, Base64.getEncoder().encodeToString(encrypted.iv()))
                .set(IP_HASH, hash)
                .set(TARGET_UUID, ipPunishment.target().uuid().toString())
                .set(TARGET_NAME, ipPunishment.target().name())
                .set(OPERATOR_UUID, ipPunishment.operator().uuid().toString())
                .set(OPERATOR_NAME, ipPunishment.operator().name())
                .set(REASON, ipPunishment.reason())
                .set(CREATED_AT, this.toOffsetDateTime(ipPunishment.createdAt()))
                .set(EXPIRES_AT, ipPunishment.expiresAtOptional().map(this::toOffsetDateTime).orElse(null))
                .set(REVOKED_AT, ipPunishment.revokedAtOptional().map(this::toOffsetDateTime).orElse(null))
                .execute();

            return null;
        });
    }

    @Override
    public CompletableFuture<Void> deactivate(UUID id) {
        return this.scheduler.completeAsync(() -> {
            this.dslContext.update(PUNISHMENT_IPS)
                .set(REVOKED_AT, this.toOffsetDateTime(Instant.now()))
                .where(ID.eq(id.toString()))
                .and(REVOKED_AT.isNull())
                .execute();

            return null;
        });
    }

    @Override
    public CompletableFuture<Optional<IpPunishment>> findActiveByIp(String ip) {
        String hash = this.ipCryptoService.hash(ip);

        return this.scheduler.completeAsync(() -> {
            OffsetDateTime now = this.toOffsetDateTime(Instant.now());

            return this.dslContext.selectFrom(PUNISHMENT_IPS)
                .where(IP_HASH.eq(hash))
                .and(REVOKED_AT.isNull())
                .and(EXPIRES_AT.isNull().or(EXPIRES_AT.gt(now)))
                .fetchOptional(this::map);
        });
    }

    @Override
    public CompletableFuture<List<IpPunishment>> findAllActive() {
        return this.scheduler.completeAsync(() -> {
            OffsetDateTime now = this.toOffsetDateTime(Instant.now());

            return this.dslContext.selectFrom(PUNISHMENT_IPS)
                .where(REVOKED_AT.isNull())
                .and(EXPIRES_AT.isNull().or(EXPIRES_AT.gt(now)))
                .fetch(this::map);
        });
    }

    @Override
    public CompletableFuture<List<IpPunishment>> findExpired(Instant now) {
        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PUNISHMENT_IPS)
            .where(REVOKED_AT.isNull())
            .and(EXPIRES_AT.isNotNull())
            .and(EXPIRES_AT.le(this.toOffsetDateTime(now)))
            .fetch(this::map));
    }

    private IpPunishment map(Record record) {
        byte[] ciphertext = Base64.getDecoder().decode(record.get(IP_CIPHERTEXT));
        byte[] iv = Base64.getDecoder().decode(record.get(IP_IV));
        String ip = this.ipCryptoService.decrypt(new EncryptedValue(ciphertext, iv));

        PunishmentTarget target = new PunishmentTarget(
            UUID.fromString(record.get(TARGET_UUID)),
            record.get(TARGET_NAME)
        );

        PunishmentTarget operator = new PunishmentTarget(
            UUID.fromString(record.get(OPERATOR_UUID)),
            record.get(OPERATOR_NAME)
        );

        OffsetDateTime expiresAt = record.get(EXPIRES_AT);
        OffsetDateTime revokedAt = record.get(REVOKED_AT);

        return IpPunishment.builder()
            .id(UUID.fromString(record.get(ID)))
            .ip(ip)
            .target(target)
            .operator(operator)
            .reason(record.get(REASON))
            .createdAt(record.get(CREATED_AT).toInstant())
            .expiresAt(expiresAt == null ? null : expiresAt.toInstant())
            .revokedAt(revokedAt == null ? null : revokedAt.toInstant())
            .build();
    }

    private OffsetDateTime toOffsetDateTime(Instant instant) {
        return instant.atOffset(ZoneOffset.UTC);
    }
}
