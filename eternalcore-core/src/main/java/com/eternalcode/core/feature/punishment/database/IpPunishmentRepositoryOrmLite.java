package com.eternalcode.core.feature.punishment.database;

import static com.eternalcode.core.feature.punishment.database.PunishmentTable.ID_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.IP_HASH_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.REVOKED_AT_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.REVOKED_BY_NAME_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.REVOKED_BY_UUID_COLUMN;
import static com.eternalcode.core.feature.punishment.database.PunishmentTable.TYPE_COLUMN;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.PunishmentType;
import com.eternalcode.core.feature.punishment.ip.IpPunishment;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.ip.IpCryptoService;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
class IpPunishmentRepositoryOrmLite extends AbstractRepositoryOrmLite implements IpPunishmentRepository {

    private static final PunishmentType IP_BAN_TYPE = PunishmentType.BAN_IP;

    private final IpCryptoService ipCryptoService;

    @Inject
    private IpPunishmentRepositoryOrmLite(
        DatabaseManager databaseManager,
        Scheduler scheduler,
        IpCryptoService ipCryptoService
    ) throws SQLException {
        super(databaseManager, scheduler);
        this.ipCryptoService = ipCryptoService;
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PunishmentTable.class);
    }

    @Override
    public CompletableFuture<Void> save(IpPunishment ipPunishment) {
        return this.action(PunishmentTable.class, dao -> dao.createOrUpdate(this.toTable(ipPunishment)))
            .thenApply(status -> null);
    }

    @Override
    public CompletableFuture<Boolean> revoke(UUID id, PunishmentTarget revokedBy, Instant revokedAt) {
        long revokedAtEpochMillis = revokedAt.toEpochMilli();

        return this.action(PunishmentTable.class, dao -> {
            UpdateBuilder<PunishmentTable, Object> builder = dao.updateBuilder();
            builder.updateColumnValue(REVOKED_AT_COLUMN, revokedAtEpochMillis);
            builder.updateColumnValue(REVOKED_BY_UUID_COLUMN, revokedBy.uuid());
            builder.updateColumnValue(REVOKED_BY_NAME_COLUMN, revokedBy.name());

            Where<PunishmentTable, Object> where = builder.where();
            where.and(
                where.eq(ID_COLUMN, id),
                where.eq(TYPE_COLUMN, IP_BAN_TYPE),
                PunishmentConditions.active(where, revokedAtEpochMillis)
            );
            return builder.update() > 0;
        });
    }

    @Override
    public CompletableFuture<Optional<IpPunishment>> findActiveByIp(String ip) {
        String ipHash = this.ipCryptoService.hash(ip);

        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(TYPE_COLUMN, IP_BAN_TYPE),
                where.eq(IP_HASH_COLUMN, ipHash),
                PunishmentConditions.active(where, this.nowEpochMillis())
            );
            return Optional.ofNullable(where.queryForFirst()).map(this::toIpPunishment);
        });
    }

    @Override
    public CompletableFuture<List<IpPunishment>> findAllActive() {
        return this.action(PunishmentTable.class, dao -> {
            Where<PunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(TYPE_COLUMN, IP_BAN_TYPE),
                PunishmentConditions.active(where, this.nowEpochMillis())
            );
            return this.toIpPunishments(where.query());
        });
    }

    private PunishmentTable toTable(IpPunishment ipPunishment) {
        String ip = ipPunishment.ip();
        return PunishmentTable.from(ipPunishment, this.ipCryptoService.encrypt(ip), this.ipCryptoService.hash(ip));
    }

    private IpPunishment toIpPunishment(PunishmentTable table) {
        return table.toIpPunishment(this.ipCryptoService.decrypt(table.encryptedIp()));
    }

    private List<IpPunishment> toIpPunishments(List<PunishmentTable> tables) {
        return tables.stream()
            .map(this::toIpPunishment)
            .toList();
    }

    private long nowEpochMillis() {
        return Instant.now().toEpochMilli();
    }
}
