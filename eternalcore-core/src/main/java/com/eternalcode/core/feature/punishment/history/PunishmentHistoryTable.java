package com.eternalcode.core.feature.punishment.history;

import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_punishment_history")
class PunishmentHistoryTable {

    static final String ID_COLUMN = "id";
    static final String PUNISHMENT_ID_COLUMN = "punishment_id";
    static final String TARGET_UUID_COLUMN = "target_uuid";
    static final String TARGET_NAME_COLUMN = "target_name";
    static final String OPERATOR_UUID_COLUMN = "operator_uuid";
    static final String OPERATOR_NAME_COLUMN = "operator_name";
    static final String ACTION_COLUMN = "action";
    static final String REASON_COLUMN = "reason";
    static final String TIMESTAMP_COLUMN = "timestamp";
    static final String EXPIRES_AT_COLUMN = "expires_at";

    private static final int PLAYER_NAME_MAX_LENGTH = 16;
    private static final int ACTION_MAX_LENGTH = 16;
    private static final int REASON_MAX_LENGTH = 255;

    @DatabaseField(columnName = ID_COLUMN, id = true)
    private UUID id;

    @DatabaseField(columnName = PUNISHMENT_ID_COLUMN, canBeNull = false)
    private UUID punishmentId;

    @DatabaseField(columnName = TARGET_UUID_COLUMN, canBeNull = false, index = true)
    private UUID targetUuid;

    @DatabaseField(columnName = TARGET_NAME_COLUMN, canBeNull = false, width = PLAYER_NAME_MAX_LENGTH)
    private String targetName;

    @DatabaseField(columnName = OPERATOR_UUID_COLUMN, canBeNull = false)
    private UUID operatorUuid;

    @DatabaseField(columnName = OPERATOR_NAME_COLUMN, canBeNull = false, width = PLAYER_NAME_MAX_LENGTH)
    private String operatorName;

    @DatabaseField(columnName = ACTION_COLUMN, canBeNull = false, width = ACTION_MAX_LENGTH, dataType = DataType.ENUM_STRING)
    private PunishmentHistoryEntry.HistoryAction action;

    @DatabaseField(columnName = REASON_COLUMN, canBeNull = false, width = REASON_MAX_LENGTH)
    private String reason;

    @DatabaseField(columnName = TIMESTAMP_COLUMN, canBeNull = false, index = true)
    private long timestamp;

    @DatabaseField(columnName = EXPIRES_AT_COLUMN)
    private Long expiresAt;

    PunishmentHistoryTable() {}

    PunishmentHistoryEntry toEntry() {
        return new PunishmentHistoryEntry(
            this.id,
            this.punishmentId,
            new PunishmentTarget(this.targetUuid, this.targetName),
            new PunishmentTarget(this.operatorUuid, this.operatorName),
            this.action,
            this.reason,
            Instant.ofEpochMilli(this.timestamp),
            this.expiresAt == null ? null : Instant.ofEpochMilli(this.expiresAt)
        );
    }

    static PunishmentHistoryTable from(PunishmentHistoryEntry entry) {
        PunishmentHistoryTable table = new PunishmentHistoryTable();
        table.id = entry.id();
        table.punishmentId = entry.punishmentId();
        table.targetUuid = entry.target().uuid();
        table.targetName = entry.target().name();
        table.operatorUuid = entry.operator().uuid();
        table.operatorName = entry.operator().name();
        table.action = entry.action();
        table.reason = entry.reason();
        table.timestamp = entry.timestamp().toEpochMilli();
        table.expiresAt = entry.expiresAtOptional().map(Instant::toEpochMilli).orElse(null);
        return table;
    }
}
