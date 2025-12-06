package com.eternalcode.core.user.database;

import com.eternalcode.core.user.User;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_users")
class UserTable {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "last_seen", dataType = DataType.DATE_STRING)
    private Instant lastSeen;

    @DatabaseField(columnName = "account_created", dataType = DataType.DATE_STRING)
    private Instant accountCreated;

    UserTable() {
    }

    UserTable(UUID uniqueId, String name, Instant lastSeen, Instant accountCreated) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.lastSeen = lastSeen;
        this.accountCreated = accountCreated;
    }

    User toUser() {
        return new User(this.uniqueId, this.name, this.lastSeen, this.accountCreated);
    }

    static UserTable from(User user) {
        return new UserTable(user.getUniqueId(), user.getName(), user.getLastSeen(), user.getAccountCreated());
    }
}
