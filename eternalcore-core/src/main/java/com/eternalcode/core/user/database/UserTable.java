package com.eternalcode.core.user.database;

import com.eternalcode.core.user.User;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_users")
public class UserTable {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "created", dataType = DataType.DATE)
    private Date created;

    @DatabaseField(columnName = "last_login", dataType = DataType.DATE)
    private Date lastLogin;

    UserTable() {}

    UserTable(UUID uniqueId, String name, Date created, Date lastLogin) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.created = created;
        this.lastLogin = lastLogin;
    }

    public User toUser() {
        return new User(this.uniqueId, this.name, this.created.toInstant(), this.lastLogin.toInstant());
    }

    public static UserTable from(User user) {
        return new UserTable(user.getUniqueId(), user.getName(), Date.from(user.getCreated()), Date.from(user.getLastLogin()));
    }
}
