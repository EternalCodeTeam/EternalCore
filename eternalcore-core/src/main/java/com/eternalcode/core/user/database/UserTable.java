package com.eternalcode.core.user.database;

import com.eternalcode.core.user.User;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_users")
public class UserTable {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "created")
    private Instant created;

    @DatabaseField(columnName = "last_login")
    private Instant lastLogin;

    UserTable() {}

    UserTable(UUID uniqueId, String name, Instant created, Instant lastLogin) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.created = created;
        this.lastLogin = lastLogin;
    }

    public User toUser() {
        return new User(this.uniqueId, this.name, this.created, this.lastLogin);
    }

    public static UserTable from(User user) {
        return new UserTable(user.getUniqueId(), user.getName(), user.getCreated(), user.getLastLogin());
    }
}
