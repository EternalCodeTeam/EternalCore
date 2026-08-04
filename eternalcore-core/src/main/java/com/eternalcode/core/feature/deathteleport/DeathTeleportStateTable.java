package com.eternalcode.core.feature.deathteleport;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_death_teleport_state")
class DeathTeleportStateTable {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "state")
    private DeathTeleportState state;

    DeathTeleportStateTable(UUID uniqueId, DeathTeleportState state) {
        this.uniqueId = uniqueId;
        this.state = state;
    }

    DeathTeleportStateTable() {}

    DeathTeleportState state() {
        return this.state;
    }
}
