package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.PunishmentType;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_punishments")
class PunishmentTable {

    static final String ID_COLUMN = "id";
    static final String TARGET_UUID_COLUMN = "target_uuid";
    static final String TARGET_NAME_COLUMN = "target_name";
    static final String OPERATOR_UUID_COLUMN = "operator_uuid";
    static final String OPERATOR_NAME_COLUMN = "operator_name";
    static final String TYPE_COLUMN = "type";
    static final String REASON_COLUMN = "reason";
    static final String CREATED_AT_COLUMN = "created_at";
    static final String EXPIRES_AT_COLUMN = "expires_at";
    static final String REVOKED_AT_COLUMN = "revoked_at";

    private static final int PLAYER_NAME_MAX_LENGTH = 32;
    private static final int TYPE_MAX_LENGTH = 16;
    private static final int REASON_MAX_LENGTH = 255;

    @DatabaseField(columnName = ID_COLUMN, id = true)
    private UUID id;

    @DatabaseField(columnName = TARGET_UUID_COLUMN, canBeNull = false, index = true)
    private UUID targetUuid;

    @DatabaseField(columnName = TARGET_NAME_COLUMN, canBeNull = false, width = PLAYER_NAME_MAX_LENGTH)
    private String targetName;

    @DatabaseField(columnName = OPERATOR_UUID_COLUMN, canBeNull = false)
    private UUID operatorUuid;

    @DatabaseField(columnName = OPERATOR_NAME_COLUMN, canBeNull = false, width = PLAYER_NAME_MAX_LENGTH)
    private String operatorName;

    @DatabaseField(columnName = TYPE_COLUMN, canBeNull = false, width = TYPE_MAX_LENGTH, dataType = DataType.ENUM_STRING)
    private PunishmentType type;

    @DatabaseField(columnName = REASON_COLUMN, canBeNull = false, width = REASON_MAX_LENGTH)
    private String reason;

    @DatabaseField(columnName = CREATED_AT_COLUMN, canBeNull = false)
    private long createdAt;

    @DatabaseField(columnName = EXPIRES_AT_COLUMN)
    private Long expiresAt;

    @DatabaseField(columnName = REVOKED_AT_COLUMN)
    private Long revokedAt;

    PunishmentTable() {}

    Punishment toPunishment() {
        return Punishment.builder()
            .id(this.id)
            .target(new PunishmentTarget(this.targetUuid, this.targetName))
            .operator(new PunishmentTarget(this.operatorUuid, this.operatorName))
            .type(this.type)
            .reason(this.reason)
            .createdAt(Instant.ofEpochMilli(this.createdAt))
            .expiresAt(toInstant(this.expiresAt))
            .revokedAt(toInstant(this.revokedAt))
            .build();
    }

    static PunishmentTable from(Punishment punishment) {
        PunishmentTable table = new PunishmentTable();
        table.id = punishment.id();
        table.targetUuid = punishment.target().uuid();
        table.targetName = punishment.target().name();
        table.operatorUuid = punishment.operator().uuid();
        table.operatorName = punishment.operator().name();
        table.type = punishment.type();
        table.reason = punishment.reason();
        table.createdAt = punishment.createdAt().toEpochMilli();
        table.expiresAt = punishment.expiresAtOptional().map(Instant::toEpochMilli).orElse(null);
        table.revokedAt = punishment.revokedAtOptional().map(Instant::toEpochMilli).orElse(null);
        return table;
    }

    private static Instant toInstant(Long epochMillis) {
        return epochMillis == null ? null : Instant.ofEpochMilli(epochMillis);
    }
}
