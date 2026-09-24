package com.eternalcode.core.feature.punishment.ip;

import static com.eternalcode.core.feature.punishment.ip.IpPunishmentTable.EXPIRES_AT_COLUMN;
import static com.eternalcode.core.feature.punishment.ip.IpPunishmentTable.ID_COLUMN;
import static com.eternalcode.core.feature.punishment.ip.IpPunishmentTable.IP_HASH_COLUMN;
import static com.eternalcode.core.feature.punishment.ip.IpPunishmentTable.REVOKED_AT_COLUMN;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
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

    private final IpCryptoService ipCryptoService;

    @Inject
    private IpPunishmentRepositoryOrmLite(
        DatabaseManager databaseManager,
        Scheduler scheduler,
        IpCryptoService ipCryptoService
    ) throws SQLException {
        super(databaseManager, scheduler);
        this.ipCryptoService = ipCryptoService;
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), IpPunishmentTable.class);
    }

    @Override
    public CompletableFuture<Void> save(IpPunishment ipPunishment) {
        return this.action(IpPunishmentTable.class, dao -> dao.createOrUpdate(this.toTable(ipPunishment)))
            .thenApply(status -> null);
    }

    @Override
    public CompletableFuture<Void> deactivate(UUID id) {
        return this.action(IpPunishmentTable.class, dao -> {
            UpdateBuilder<IpPunishmentTable, Object> builder = dao.updateBuilder();
            builder.updateColumnValue(REVOKED_AT_COLUMN, this.nowEpochMillis());
            builder.where()
                .eq(ID_COLUMN, id)
                .and()
                .isNull(REVOKED_AT_COLUMN);
            return builder.update();
        }).thenApply(updatedRows -> null);
    }

    @Override
    public CompletableFuture<Optional<IpPunishment>> findActiveByIp(String ip) {
        String ipHash = this.ipCryptoService.hash(ip);

        return this.action(IpPunishmentTable.class, dao -> {
            Where<IpPunishmentTable, Object> where = dao.queryBuilder().where();
            where.and(
                where.eq(IP_HASH_COLUMN, ipHash),
                this.active(where, this.nowEpochMillis())
            );
            return Optional.ofNullable(where.queryForFirst()).map(this::toIpPunishment);
        });
    }

    @Override
    public CompletableFuture<List<IpPunishment>> findAllActive() {
        return this.action(IpPunishmentTable.class, dao -> {
            Where<IpPunishmentTable, Object> where = dao.queryBuilder().where();
            this.active(where, this.nowEpochMillis());
            return this.toIpPunishments(where.query());
        });
    }

    @Override
    public CompletableFuture<List<IpPunishment>> findExpired(Instant now) {
        return this.action(IpPunishmentTable.class, dao -> {
            Where<IpPunishmentTable, Object> where = dao.queryBuilder().where();
            where.isNull(REVOKED_AT_COLUMN)
                .and()
                .isNotNull(EXPIRES_AT_COLUMN)
                .and()
                .le(EXPIRES_AT_COLUMN, now.toEpochMilli());
            return this.toIpPunishments(where.query());
        });
    }

    private Where<IpPunishmentTable, Object> active(Where<IpPunishmentTable, Object> where, long nowEpochMillis)
        throws SQLException {
        return where.and(
            where.isNull(REVOKED_AT_COLUMN),
            where.or(
                where.isNull(EXPIRES_AT_COLUMN),
                where.gt(EXPIRES_AT_COLUMN, nowEpochMillis)
            )
        );
    }

    private IpPunishmentTable toTable(IpPunishment ipPunishment) {
        String ip = ipPunishment.ip();
        return IpPunishmentTable.from(ipPunishment, this.ipCryptoService.encrypt(ip), this.ipCryptoService.hash(ip));
    }

    private IpPunishment toIpPunishment(IpPunishmentTable table) {
        return table.toIpPunishment(this.ipCryptoService.decrypt(table.encryptedIp()));
    }

    private List<IpPunishment> toIpPunishments(List<IpPunishmentTable> tables) {
        return tables.stream()
            .map(this::toIpPunishment)
            .toList();
    }

    private long nowEpochMillis() {
        return Instant.now().toEpochMilli();
    }
}
