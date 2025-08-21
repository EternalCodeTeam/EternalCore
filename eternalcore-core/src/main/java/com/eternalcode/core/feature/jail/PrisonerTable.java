package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

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

    @DatabaseField(columnName = "last_position", dataType =  DataType.SERIALIZABLE)
    @Nullable
    private Position lastPosition;

    PrisonerTable() {
    }

    PrisonerTable(UUID uuid, Instant detainedAt, Duration duration, String detainedBy) {
        this(uuid, detainedAt, duration, detainedBy, null);
    }

    PrisonerTable(UUID uuid, Instant detainedAt, Duration duration, String detainedBy, @Nullable Position lastPosition) {
        this.uuid = uuid;
        this.detainedAt = detainedAt;
        this.duration = duration;
        this.detainedBy = detainedBy;
        this.lastPosition = lastPosition;
    }

    JailedPlayer toPrisoner() {
        return new JailedPlayer(this.uuid, this.detainedAt, this.duration, this.detainedBy, this.lastPosition);
    }

    static PrisonerTable from(JailedPlayer jailedPlayer) {
        return new PrisonerTable(
            jailedPlayer.getPlayerUniqueId(),
            jailedPlayer.getDetainedAt(),
            jailedPlayer.getPrisonTime(),
            jailedPlayer.getDetainedBy(),
            jailedPlayer.getLastPosition()
        );
    }
}
