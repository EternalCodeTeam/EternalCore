package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.commons.scheduler.Scheduler;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import org.bukkit.Bukkit;


import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

@Repository
class JailLocationRepositoryOrmLite extends AbstractRepositoryOrmLite implements JailLocationRepository {

    @Inject
    private JailLocationRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), JailPositionWrapper.class);
    }

    @Override
    public CompletableFuture<Optional<Position>> getJailLocation() {
        return this.selectSafe(JailPositionWrapper.class, 1)
            .thenApply(optional -> optional.map(JailPositionWrapper::toPosition));
    }

    @Override
    public void setJailLocation(Position position) {
        this.save(JailPositionWrapper.class, new JailPositionWrapper(position));
    }

    @Override
    public void deleteJailLocation() {
        this.delete(JailPositionWrapper.class, new JailPositionWrapper());
    }

    @DatabaseTable(tableName = "eternal_core_jail_location")
    static class JailPositionWrapper {

        @DatabaseField(columnName = "id", id = true)
        private final int id = 1;  // Only one record, so a constant ID

        @DatabaseField(columnName = "world")
        private String world;

        @DatabaseField(columnName = "x")
        private double x;

        @DatabaseField(columnName = "y")
        private double y;

        @DatabaseField(columnName = "z")
        private double z;

        @DatabaseField(columnName = "yaw")
        private float yaw;

        @DatabaseField(columnName = "pitch")
        private float pitch;

        JailPositionWrapper() {
        }

        JailPositionWrapper(Position position) {
            this.world = position.world();
            this.x = position.x();
            this.y = position.y();
            this.z = position.z();
            this.yaw = position.yaw();
            this.pitch = position.pitch();
        }

        Position toPosition() {
            try {
                return new Position(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
            }
            catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "Error converting JailLocationWrapper to Position", e);
                return null;
            }
        }
    }
}
