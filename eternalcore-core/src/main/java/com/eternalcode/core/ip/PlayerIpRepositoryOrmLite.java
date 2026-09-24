package com.eternalcode.core.ip;

import static com.eternalcode.core.ip.PlayerIpTable.IP_HASH_COLUMN;
import static com.eternalcode.core.ip.PlayerIpTable.LAST_SEEN_COLUMN;
import static com.eternalcode.core.ip.PlayerIpTable.TARGET_UUID_COLUMN;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.AbstractRepositoryOrmLite;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
class PlayerIpRepositoryOrmLite extends AbstractRepositoryOrmLite implements PlayerIpRepository {

    private static final boolean DESCENDING = false;

    private final IpCryptoService ipCryptoService;

    @Inject
    private PlayerIpRepositoryOrmLite(
        DatabaseManager databaseManager,
        Scheduler scheduler,
        IpCryptoService ipCryptoService
    ) throws SQLException {
        super(databaseManager, scheduler);
        this.ipCryptoService = ipCryptoService;
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PlayerIpTable.class);
    }

    @Override
    public CompletableFuture<Void> recordLogin(UUID targetUuid, String targetName, String ip) {
        return this.action(PlayerIpTable.class, dao -> {
            String ipHash = this.ipCryptoService.hash(ip);
            long now = Instant.now().toEpochMilli();

            PlayerIpTable existing = this.findByTargetAndHash(dao, targetUuid, ipHash);

            if (existing != null) {
                existing.markSeen(targetName, now);
                return dao.update(existing);
            }

            EncryptedValue encryptedIp = this.ipCryptoService.encrypt(ip);
            return dao.create(PlayerIpTable.create(targetUuid, targetName, encryptedIp, ipHash, now));
        }).thenApply(affectedRows -> null);
    }

    @Override
    public CompletableFuture<Optional<PlayerIpEntry>> findLatest(UUID targetUuid) {
        return this.action(PlayerIpTable.class, dao -> Optional.ofNullable(dao.queryBuilder()
                .orderBy(LAST_SEEN_COLUMN, DESCENDING)
                .where()
                .eq(TARGET_UUID_COLUMN, targetUuid)
                .queryForFirst())
            .map(this::toEntry));
    }

    @Override
    public CompletableFuture<List<PlayerIpEntry>> findAllByTarget(UUID targetUuid) {
        return this.action(PlayerIpTable.class, dao -> this.toEntries(dao.queryBuilder()
            .orderBy(LAST_SEEN_COLUMN, DESCENDING)
            .where()
            .eq(TARGET_UUID_COLUMN, targetUuid)
            .query()));
    }

    @Override
    public CompletableFuture<List<PlayerIpEntry>> findAllByIp(String ip) {
        String ipHash = this.ipCryptoService.hash(ip);

        return this.action(PlayerIpTable.class, dao -> this.toEntries(dao.queryBuilder()
            .where()
            .eq(IP_HASH_COLUMN, ipHash)
            .query()));
    }

    @Override
    public CompletableFuture<Void> deleteOlderThan(Instant threshold) {
        return this.action(PlayerIpTable.class, dao -> {
            DeleteBuilder<PlayerIpTable, Object> builder = dao.deleteBuilder();
            builder.where().lt(LAST_SEEN_COLUMN, threshold.toEpochMilli());
            return builder.delete();
        }).thenApply(deletedRows -> null);
    }

    private PlayerIpTable findByTargetAndHash(Dao<PlayerIpTable, Object> dao, UUID targetUuid, String ipHash)
        throws SQLException {
        return dao.queryBuilder()
            .where()
            .eq(TARGET_UUID_COLUMN, targetUuid)
            .and()
            .eq(IP_HASH_COLUMN, ipHash)
            .queryForFirst();
    }

    private PlayerIpEntry toEntry(PlayerIpTable table) {
        return table.toEntry(this.ipCryptoService.decrypt(table.encryptedIp()));
    }

    private List<PlayerIpEntry> toEntries(List<PlayerIpTable> tables) {
        return tables.stream()
            .map(this::toEntry)
            .toList();
    }
}
