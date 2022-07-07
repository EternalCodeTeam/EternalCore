package com.eternalcode.core.database.wrapper;

import com.eternalcode.core.database.persister.LocationPersister;
import com.eternalcode.core.home.Home;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.bukkit.Location;

import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_homes")
class HomeWrapper {

    @DatabaseField(columnName = "id", id = true)
    private UUID uuid;

    @DatabaseField(columnName = "owner")
    private UUID owner;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "location", persisterClass = LocationPersister.class)
    private Location location;

    HomeWrapper() {}

    HomeWrapper(UUID uuid, UUID owner, String name, Location location) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.location = location;
    }

    Home toHome() {
        return new Home(uuid, owner, name, location);
    }

    static HomeWrapper from(Home home) {
        return new HomeWrapper(home.getUuid(), home.getOwner(), home.getName(), home.getLocation());
    }

}
