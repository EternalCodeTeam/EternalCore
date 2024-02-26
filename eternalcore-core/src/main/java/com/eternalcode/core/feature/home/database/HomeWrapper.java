package com.eternalcode.core.feature.home.database;

import com.eternalcode.core.database.persister.LocationPersister;
import com.eternalcode.core.feature.home.HomeImpl;
import com.eternalcode.core.feature.home.Home;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;
import org.bukkit.Location;

@DatabaseTable(tableName = "eternal_core_homes")
public class HomeWrapper {

    @DatabaseField(columnName = "id", id = true)
    private UUID uuid;

    @DatabaseField(columnName = "owner")
    private UUID owner;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "location", persisterClass = LocationPersister.class)
    private Location location;

    HomeWrapper() {
    }

    public HomeWrapper(UUID uuid, UUID owner, String name, Location location) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.location = location;
    }

    Home toHome() {
        return new HomeImpl(this.uuid, this.owner, this.name, this.location);
    }

    static HomeWrapper from(Home home) {
        return new HomeWrapper(home.getUuid(), home.getOwner(), home.getName(), home.getLocation());
    }
}

