package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.Revocation;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;
import java.util.UUID;

/**
 * Single table for every punishment domain. Kind specific data lives in {@link PunishmentDetails}.
 * Revocations are rows of their own (see {@link PunishmentKind#revocation()}).
 */
@DatabaseTable(tableName = "eternal_core_punishments")
class PunishmentTable {

    static final String ID_COLUMN = "id";
    static final String KIND_COLUMN = "kind";
    static final String TARGET_UUID_COLUMN = "target_uuid";
    static final String TARGET_NAME_COLUMN = "target_name";
    static final String OPERATOR_UUID_COLUMN = "operator_uuid";
    static final String OPERATOR_NAME_COLUMN = "operator_name";
    static final String REASON_COLUMN = "reason";
    static final String CREATED_AT_COLUMN = "created_at";
    static final String EXPIRES_AT_COLUMN = "expires_at";
    static final String DETAILS_COLUMN = "details";

    private static final String REVOKED_ID_KEY = "revokedId";
    private static final String NO_REASON = "";

    private static final int PLAYER_NAME_MAX_LENGTH = 32;
    private static final int KIND_MAX_LENGTH = 24;

    @DatabaseField(columnName = ID_COLUMN, id = true)
    private UUID id;

    @DatabaseField(columnName = KIND_COLUMN, canBeNull = false, width = KIND_MAX_LENGTH, dataType = DataType.ENUM_STRING, index = true)
    private PunishmentKind kind;

    @DatabaseField(columnName = TARGET_UUID_COLUMN, canBeNull = false, index = true)
    private UUID targetUuid;

    @DatabaseField(columnName = TARGET_NAME_COLUMN, canBeNull = false, width = PLAYER_NAME_MAX_LENGTH)
    private String targetName;

    @DatabaseField(columnName = OPERATOR_UUID_COLUMN, canBeNull = false)
    private UUID operatorUuid;

    @DatabaseField(columnName = OPERATOR_NAME_COLUMN, canBeNull = false, width = PLAYER_NAME_MAX_LENGTH)
    private String operatorName;

    @DatabaseField(columnName = REASON_COLUMN, canBeNull = false, dataType = DataType.LONG_STRING)
    private String reason;

    @DatabaseField(columnName = CREATED_AT_COLUMN, canBeNull = false, index = true)
    private long createdAt;

    @DatabaseField(columnName = EXPIRES_AT_COLUMN)
    private Long expiresAt;

    @DatabaseField(columnName = DETAILS_COLUMN, canBeNull = false, dataType = DataType.LONG_STRING)
    private String details;

    PunishmentTable() {
    }

    static PunishmentTable of(PunishmentKind kind, Punishment punishment, PunishmentDetails details) {
        PunishmentTable table = new PunishmentTable();
        table.id = punishment.id();
        table.kind = kind;
        table.targetUuid = punishment.target().uuid();
        table.targetName = punishment.target().name();
        table.operatorUuid = punishment.operator().uuid();
        table.operatorName = punishment.operator().name();
        table.reason = punishment.reason();
        table.createdAt = punishment.createdAt().toEpochMilli();
        table.expiresAt = punishment.expiresAtOptional().map(Instant::toEpochMilli).orElse(null);
        table.details = details.serialize();
        return table;
    }

    /**
     * Row describing that {@code original} was revoked (unban, unmute, ...) by {@code revokedBy}.
     */
    static PunishmentTable revocationOf(PunishmentTable original, PunishmentTarget revokedBy, Instant revokedAt) {
        PunishmentTable table = new PunishmentTable();
        table.id = UUID.randomUUID();
        table.kind = original.kind.revocation();
        table.targetUuid = original.targetUuid;
        table.targetName = original.targetName;
        table.operatorUuid = revokedBy.uuid();
        table.operatorName = revokedBy.name();
        table.reason = NO_REASON;
        table.createdAt = revokedAt.toEpochMilli();
        table.expiresAt = null;
        table.details = PunishmentDetails.empty().with(REVOKED_ID_KEY, original.id.toString()).serialize();
        return table;
    }

    UUID id() {
        return this.id;
    }

    PunishmentKind kind() {
        return this.kind;
    }

    PunishmentTarget target() {
        return new PunishmentTarget(this.targetUuid, this.targetName);
    }

    PunishmentTarget operator() {
        return new PunishmentTarget(this.operatorUuid, this.operatorName);
    }

    String reason() {
        return this.reason;
    }

    Instant createdAt() {
        return Instant.ofEpochMilli(this.createdAt);
    }

    long createdAtMillis() {
        return this.createdAt;
    }

    Instant expiresAt() {
        return this.expiresAt == null ? null : Instant.ofEpochMilli(this.expiresAt);
    }

    PunishmentDetails details() {
        return PunishmentDetails.parse(this.details);
    }

    UUID revokedId() {
        this.requireRevocation();

        return UUID.fromString(this.details().require(REVOKED_ID_KEY));
    }

    Revocation toRevocation() {
        this.requireRevocation();

        return new Revocation(this.operator(), this.createdAt());
    }

    private void requireRevocation() {
        if (!this.kind.isRevocation()) {
            throw new IllegalStateException("Punishment " + this.id + " is " + this.kind + ", not a revocation");
        }
    }
}
