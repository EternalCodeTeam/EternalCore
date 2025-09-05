package com.eternalcode.core.user.database;

import com.eternalcode.core.user.User;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_users")
public class UserTable {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "name")
    private String name;

    UserTable() {}

    UserTable(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public User toUser() {
        return new User(this.uniqueId, this.name);
    }

    public static UserTable from(User user) {
        return new UserTable(user.getUniqueId(), user.getName());
    }
}
