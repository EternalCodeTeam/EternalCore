package com.eternalcode.core.feature.jail;

import com.eternalcode.core.database.persister.LocationPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.bukkit.Location;

@DatabaseTable(tableName = "eternal_core_prisoners")
class PrisonerTable {

    @DatabaseField(columnName = "id", id = true)
    private UUID uuid;

    @DatabaseField(columnName = "detained_at", dataType = DataType.SERIALIZABLE)
    private Instant detainedAt;

    @DatabaseField(columnName = "duration", dataType = DataType.SERIALIZABLE)
    private Duration duration;

    @DatabaseField(columnName = "detained_by")
    private String detainedBy;

    @DatabaseField(
        columnName = "last_location",
        persisterClass = LocationPersister.class,
        dataType = DataType.LONG_STRING
    )
    private Location lastLocation;

    PrisonerTable() {
    }

    PrisonerTable(UUID uuid, Instant detainedAt, Duration duration, String detainedBy, Location lastLocation) {
        this.uuid = uuid;
        this.detainedAt = detainedAt;
        this.duration = duration;
        this.detainedBy = detainedBy;
        this.lastLocation = lastLocation;
    }

    static PrisonerTable from(JailedPlayer jailedPlayer) {
        return new PrisonerTable(
            jailedPlayer.playerUniqueId(),
            jailedPlayer.detainedAt(),
            jailedPlayer.prisonTime(),
            jailedPlayer.detainedBy(),
            jailedPlayer.lastLocation()
        );
    }

    JailedPlayer toPrisoner() {
        return new JailedPlayerImpl(
            this.uuid,
            this.detainedAt,
            this.duration,
            this.detainedBy,
            this.lastLocation
        );
    }
}
