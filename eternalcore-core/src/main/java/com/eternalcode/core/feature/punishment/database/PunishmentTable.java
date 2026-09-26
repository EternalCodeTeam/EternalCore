package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.PunishmentType;
import com.eternalcode.core.feature.punishment.ip.IpPunishment;
import com.eternalcode.core.ip.EncryptedValue;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;
import java.util.Base64;
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
    static final String REVOKED_BY_UUID_COLUMN = "revoked_by_uuid";
    static final String REVOKED_BY_NAME_COLUMN = "revoked_by_name";
    static final String IP_CIPHERTEXT_COLUMN = "ip_ciphertext";
    static final String IP_IV_COLUMN = "ip_iv";
    static final String IP_HASH_COLUMN = "ip_hash";

    private static final int PLAYER_NAME_MAX_LENGTH = 32;
    private static final int TYPE_MAX_LENGTH = 16;
    private static final int REASON_MAX_LENGTH = 255;
    private static final int IP_CIPHERTEXT_MAX_LENGTH = 255;
    private static final int IP_IV_MAX_LENGTH = 64;
    private static final int IP_HASH_MAX_LENGTH = 64;

    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

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

    @DatabaseField(columnName = REVOKED_BY_UUID_COLUMN)
    private UUID revokedByUuid;

    @DatabaseField(columnName = REVOKED_BY_NAME_COLUMN, width = PLAYER_NAME_MAX_LENGTH)
    private String revokedByName;

    // Filled only for PunishmentType.BAN_IP
    @DatabaseField(columnName = IP_CIPHERTEXT_COLUMN, width = IP_CIPHERTEXT_MAX_LENGTH)
    private String ipCiphertext;

    @DatabaseField(columnName = IP_IV_COLUMN, width = IP_IV_MAX_LENGTH)
    private String ipIv;

    @DatabaseField(columnName = IP_HASH_COLUMN, width = IP_HASH_MAX_LENGTH, index = true)
    private String ipHash;

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
            .revokedBy(this.revokedBy())
            .build();
    }

    EncryptedValue encryptedIp() {
        this.requireIpPunishment();

        return new EncryptedValue(
            BASE64_DECODER.decode(this.ipCiphertext),
            BASE64_DECODER.decode(this.ipIv)
        );
    }

    IpPunishment toIpPunishment(String decryptedIp) {
        this.requireIpPunishment();

        return IpPunishment.builder()
            .id(this.id)
            .ip(decryptedIp)
            .target(new PunishmentTarget(this.targetUuid, this.targetName))
            .operator(new PunishmentTarget(this.operatorUuid, this.operatorName))
            .reason(this.reason)
            .createdAt(Instant.ofEpochMilli(this.createdAt))
            .expiresAt(toInstant(this.expiresAt))
            .revokedAt(toInstant(this.revokedAt))
            .revokedBy(this.revokedBy())
            .build();
    }

    static PunishmentTable from(Punishment punishment) {
        if (punishment.type() == PunishmentType.BAN_IP) {
            throw new IllegalArgumentException("BAN_IP must be stored via from(IpPunishment, EncryptedValue, String)");
        }

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
        table.revokedByUuid = punishment.revokedByOptional().map(PunishmentTarget::uuid).orElse(null);
        table.revokedByName = punishment.revokedByOptional().map(PunishmentTarget::name).orElse(null);
        return table;
    }

    static PunishmentTable from(IpPunishment ipPunishment, EncryptedValue encryptedIp, String ipHash) {
        PunishmentTable table = new PunishmentTable();
        table.id = ipPunishment.id();
        table.targetUuid = ipPunishment.target().uuid();
        table.targetName = ipPunishment.target().name();
        table.operatorUuid = ipPunishment.operator().uuid();
        table.operatorName = ipPunishment.operator().name();
        table.type = PunishmentType.BAN_IP;
        table.reason = ipPunishment.reason();
        table.createdAt = ipPunishment.createdAt().toEpochMilli();
        table.expiresAt = ipPunishment.expiresAtOptional().map(Instant::toEpochMilli).orElse(null);
        table.revokedAt = ipPunishment.revokedAtOptional().map(Instant::toEpochMilli).orElse(null);
        table.revokedByUuid = ipPunishment.revokedByOptional().map(PunishmentTarget::uuid).orElse(null);
        table.revokedByName = ipPunishment.revokedByOptional().map(PunishmentTarget::name).orElse(null);
        table.ipCiphertext = BASE64_ENCODER.encodeToString(encryptedIp.ciphertext());
        table.ipIv = BASE64_ENCODER.encodeToString(encryptedIp.iv());
        table.ipHash = ipHash;
        return table;
    }

    private PunishmentTarget revokedBy() {
        if (this.revokedByUuid == null) {
            return null;
        }

        return new PunishmentTarget(this.revokedByUuid, this.revokedByName);
    }

    private void requireIpPunishment() {
        if (this.type != PunishmentType.BAN_IP) {
            throw new IllegalStateException("Punishment " + this.id + " is " + this.type + ", not BAN_IP");
        }

        if (this.ipCiphertext == null || this.ipIv == null || this.ipHash == null) {
            throw new IllegalStateException("BAN_IP punishment " + this.id + " has missing IP columns");
        }
    }

    private static Instant toInstant(Long epochMillis) {
        return epochMillis == null ? null : Instant.ofEpochMilli(epochMillis);
    }
}
