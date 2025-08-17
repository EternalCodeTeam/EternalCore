package com.eternalcode.core.feature.jail;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

    PrisonerTable() {
    }

    PrisonerTable(UUID uuid, Instant detainedAt, Duration duration, String detainedBy) {
        this.uuid = uuid;
        this.detainedAt = detainedAt;
        this.duration = duration;
        this.detainedBy = detainedBy;
    }

    JailedPlayer toPrisoner() {
        return new JailedPlayer(this.uuid, this.detainedAt, this.duration, this.detainedBy);
    }

    static PrisonerTable from(JailedPlayer jailedPlayer) {
        return new PrisonerTable(
            jailedPlayer.getPlayerUniqueId(),
            jailedPlayer.getDetainedAt(),
            jailedPlayer.getPrisonTime(),
            jailedPlayer.getDetainedBy()
        );
    }
}
