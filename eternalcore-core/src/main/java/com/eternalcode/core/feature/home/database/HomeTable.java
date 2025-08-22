package com.eternalcode.core.feature.home.database;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.database.persister.PositionPersister;
import com.eternalcode.core.feature.home.HomeImpl;
import com.eternalcode.core.feature.home.Home;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_homes")
class HomeTable {

    @DatabaseField(columnName = "id", id = true)
    private UUID uuid;

    @DatabaseField(columnName = "owner")
    private UUID owner;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "location", persisterClass = PositionPersister.class)
    private Position position;

    HomeTable() {}

    HomeTable(UUID uuid, UUID owner, String name, Position position) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.position = position;
    }

    Home toHome() {
        return new HomeImpl(this.uuid, this.owner, this.name, this.position);
    }

    static HomeTable from(Home home) {
        return new HomeTable(home.getUuid(), home.getOwner(), home.getName(), home.getPosition());
    }
}

