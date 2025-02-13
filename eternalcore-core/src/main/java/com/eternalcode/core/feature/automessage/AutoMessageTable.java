package com.eternalcode.core.feature.automessage;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_auto_message_ignore")
class AutoMessageTable {

    @DatabaseField(columnName = "unique_id", id = true)
    UUID uniqueId;

    AutoMessageTable() {}

    AutoMessageTable(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}
