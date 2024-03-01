package com.eternalcode.core.feature.jail;

import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.commons.scheduler.Scheduler;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import panda.std.reactive.Completable;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

@Repository
class JailLocationRepositoryOrmLite extends AbstractRepositoryOrmLite implements JailLocationRepository {

    @Inject
    private JailLocationRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), JailLocationWrapper.class);
    }

    @Override
    public CompletableFuture<Optional<Location>> getJailLocation() {
        return this.select(JailLocationWrapper.class, new JailLocationWrapper())
            .thenApply(Optional::of)
            .thenApply(jailLocation -> jailLocation.map(JailLocationWrapper::toLocation));
    }

    @Override
    public void setJailLocation(Location location) {
        JailLocationWrapper jailLocationWrapper = new JailLocationWrapper(location);
        this.save(JailLocationWrapper.class, jailLocationWrapper);
    }

    @Override
    public void deleteJailLocation() {
        this.delete(JailLocationWrapper.class, new JailLocationWrapper());
    }

    @DatabaseTable(tableName = "eternal_core_jail_location")
    static class JailLocationWrapper {

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

        JailLocationWrapper() {
        }

        JailLocationWrapper(Location location) {
            this.world = location.getWorld().getName();
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.yaw = location.getYaw();
            this.pitch = location.getPitch();
        }

        Location toLocation() {
            try {
                return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
            }
            catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "Error converting JailLocationWrapper to Location", e);
                return null;
            }
        }
    }
}
