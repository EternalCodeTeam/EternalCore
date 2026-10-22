package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.ExpirablePunishment;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentKind;
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
public class PunishmentTable {

    public static final String ID_COLUMN = "id";
    public static final String KIND_COLUMN = "kind";
    public static final String TARGET_UUID_COLUMN = "target_uuid";
    public static final String TARGET_NAME_COLUMN = "target_name";
    public static final String OPERATOR_UUID_COLUMN = "operator_uuid";
    public static final String OPERATOR_NAME_COLUMN = "operator_name";
    public static final String REASON_COLUMN = "reason";
    public static final String CREATED_AT_COLUMN = "created_at";
    public static final String DETAILS_COLUMN = "details";

    private static final String REVOKED_ID_KEY = "revokedId";
    private static final String EXPIRES_AT_KEY = "expiresAt";
    private static final String NO_REASON = "";
    // storage-only marker of console rows, never exposed through the API
    private static final UUID CONSOLE_OPERATOR_UUID = UUID.nameUUIDFromBytes("CONSOLE_UUID".getBytes());

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

    @DatabaseField(columnName = DETAILS_COLUMN, canBeNull = false, dataType = DataType.LONG_STRING)
    private String details;

    public PunishmentTable() {
    }

    public static PunishmentTable of(PunishmentKind kind, Punishment punishment, PunishmentDetails details) {
        PunishmentTable table = new PunishmentTable();
        table.id = punishment.id();
        table.kind = kind;
        table.targetUuid = punishment.target().uuid();
        table.targetName = punishment.target().name();
        table.operatorUuid = operatorUuidOf(punishment.operator());
        table.operatorName = punishment.operator().name();
        table.reason = punishment.reason();
        table.createdAt = punishment.createdAt().toEpochMilli();
        table.details = detailsWithExpiration(punishment, details).serialize();
        return table;
    }

    private static PunishmentDetails detailsWithExpiration(Punishment punishment, PunishmentDetails details) {
        if (!(punishment instanceof ExpirablePunishment expirable)) {
            return details;
        }

        return expirable.expiresAtOptional()
            .map(expiresAt -> details.with(EXPIRES_AT_KEY, String.valueOf(expiresAt.toEpochMilli())))
            .orElse(details);
    }

    private static UUID operatorUuidOf(PunishmentActor actor) {
        return switch (actor) {
            case PunishmentActor.Player player -> player.uuid();
            case PunishmentActor.Console console -> CONSOLE_OPERATOR_UUID;
        };
    }

    /**
     * Row describing that {@code original} was revoked (unban, unmute, ...) by {@code revokedBy}.
     */
    public static PunishmentTable revocationOf(PunishmentTable original, PunishmentActor revokedBy, Instant revokedAt) {
        PunishmentTable table = new PunishmentTable();
        table.id = UUID.randomUUID();
        table.kind = original.kind.revocation();
        table.targetUuid = original.targetUuid;
        table.targetName = original.targetName;
        table.operatorUuid = operatorUuidOf(revokedBy);
        table.operatorName = revokedBy.name();
        table.reason = NO_REASON;
        table.createdAt = revokedAt.toEpochMilli();
        table.details = PunishmentDetails.empty().with(REVOKED_ID_KEY, original.id.toString()).serialize();
        return table;
    }

    public UUID id() {
        return this.id;
    }

    public PunishmentKind kind() {
        return this.kind;
    }

    public PunishmentTarget target() {
        return new PunishmentTarget(this.targetUuid, this.targetName);
    }

    public PunishmentActor operator() {
        if (CONSOLE_OPERATOR_UUID.equals(this.operatorUuid)) {
            return new PunishmentActor.Console(this.operatorName);
        }

        return new PunishmentActor.Player(this.operatorUuid, this.operatorName);
    }

    public String reason() {
        return this.reason;
    }

    public Instant createdAt() {
        return Instant.ofEpochMilli(this.createdAt);
    }

    public long createdAtMillis() {
        return this.createdAt;
    }

    public Instant expiresAt() {
        return this.details().find(EXPIRES_AT_KEY)
            .map(Long::parseLong)
            .map(Instant::ofEpochMilli)
            .orElse(null);
    }

    public PunishmentDetails details() {
        return PunishmentDetails.parse(this.details);
    }

    public UUID revokedId() {
        this.requireRevocation();

        return UUID.fromString(this.details().require(REVOKED_ID_KEY));
    }

    public Revocation toRevocation() {
        this.requireRevocation();

        return new Revocation(this.operator(), this.createdAt());
    }

    private void requireRevocation() {
        if (!this.kind.isRevocation()) {
            throw new IllegalStateException("Punishment " + this.id + " is " + this.kind + ", not a revocation");
        }
    }
}
