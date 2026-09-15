package com.eternalcode.core.ip;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.eternalcode.core.ip.PlayerIpSchema.*;

@Repository
class PlayerIpRepositoryImpl implements PlayerIpRepository {

    private final DSLContext dslContext;
    private final Scheduler scheduler;
    private final IpCryptoService ipCryptoService;

    @Inject
    PlayerIpRepositoryImpl(DSLContext dslContext, Scheduler scheduler, IpCryptoService ipCryptoService) {
        this.dslContext = dslContext;
        this.scheduler = scheduler;
        this.ipCryptoService = ipCryptoService;

        this.createTable();
    }

    private void createTable() {
        this.dslContext.createTableIfNotExists(PLAYER_IPS)
            .column(ID)
            .column(IP_CIPHERTEXT)
            .column(IP_IV)
            .column(IP_HASH)
            .column(TARGET_UUID)
            .column(TARGET_NAME)
            .column(FIRST_SEEN)
            .column(LAST_SEEN)
            .constraints(DSL.constraint("pk_eternalcore_player_ips").primaryKey(ID))
            .execute();
    }

    @Override
    public CompletableFuture<Void> recordLogin(UUID targetUuid, String targetName, String ip) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");
        Objects.requireNonNull(targetName, "targetName cannot be null");
        Objects.requireNonNull(ip, "ip cannot be null");

        return this.scheduler.completeAsync(() -> {
            String hash = this.ipCryptoService.hash(ip);
            OffsetDateTime now = this.toOffsetDateTime(Instant.now());

            String existingId = this.dslContext.select(ID)
                .from(PLAYER_IPS)
                .where(TARGET_UUID.eq(targetUuid.toString()))
                .and(IP_HASH.eq(hash))
                .fetchOne(ID);

            if (existingId != null) {
                this.dslContext.update(PLAYER_IPS)
                    .set(LAST_SEEN, now)
                    .set(TARGET_NAME, targetName)
                    .where(ID.eq(existingId))
                    .execute();

                return null;
            }

            EncryptedValue encrypted = this.ipCryptoService.encrypt(ip);

            this.dslContext.insertInto(PLAYER_IPS)
                .set(ID, UUID.randomUUID().toString())
                .set(IP_CIPHERTEXT, Base64.getEncoder().encodeToString(encrypted.ciphertext()))
                .set(IP_IV, Base64.getEncoder().encodeToString(encrypted.iv()))
                .set(IP_HASH, hash)
                .set(TARGET_UUID, targetUuid.toString())
                .set(TARGET_NAME, targetName)
                .set(FIRST_SEEN, now)
                .set(LAST_SEEN, now)
                .execute();

            return null;
        });
    }

    @Override
    public CompletableFuture<Optional<PlayerIpEntry>> findLatest(UUID targetUuid) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");

        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PLAYER_IPS)
            .where(TARGET_UUID.eq(targetUuid.toString()))
            .orderBy(LAST_SEEN.desc())
            .limit(1)
            .fetchOptional(this::map));
    }

    @Override
    public CompletableFuture<List<PlayerIpEntry>> findAllByTarget(UUID targetUuid) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");

        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PLAYER_IPS)
            .where(TARGET_UUID.eq(targetUuid.toString()))
            .orderBy(LAST_SEEN.desc())
            .fetch(this::map));
    }

    @Override
    public CompletableFuture<List<PlayerIpEntry>> findAllByIp(String ip) {
        Objects.requireNonNull(ip, "ip cannot be null");

        String hash = this.ipCryptoService.hash(ip);

        return this.scheduler.completeAsync(() -> this.dslContext.selectFrom(PLAYER_IPS)
            .where(IP_HASH.eq(hash))
            .fetch(this::map));
    }

    @Override
    public CompletableFuture<Void> deleteOlderThan(Instant threshold) {
        Objects.requireNonNull(threshold, "threshold cannot be null");

        return this.scheduler.completeAsync(() -> {
            this.dslContext.deleteFrom(PLAYER_IPS)
                .where(LAST_SEEN.lt(this.toOffsetDateTime(threshold)))
                .execute();
            return null;
        });
    }

    private PlayerIpEntry map(Record record) {
        byte[] ciphertext = Base64.getDecoder().decode(record.get(IP_CIPHERTEXT));
        byte[] iv = Base64.getDecoder().decode(record.get(IP_IV));
        String ip = this.ipCryptoService.decrypt(new EncryptedValue(ciphertext, iv));

        return new PlayerIpEntry(
            UUID.fromString(record.get(ID)),
            UUID.fromString(record.get(TARGET_UUID)),
            record.get(TARGET_NAME),
            ip,
            record.get(FIRST_SEEN).toInstant(),
            record.get(LAST_SEEN).toInstant()
        );
    }

    private OffsetDateTime toOffsetDateTime(Instant instant) {
        return instant.atOffset(ZoneOffset.UTC);
    }
}
