package com.eternalcode.core.feature.punishment.ip;

import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.ip.EncryptedValue;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_punishment_ips")
class IpPunishmentTable {

    static final String ID_COLUMN = "id";
    static final String IP_CIPHERTEXT_COLUMN = "ip_ciphertext";
    static final String IP_IV_COLUMN = "ip_iv";
    static final String IP_HASH_COLUMN = "ip_hash";
    static final String TARGET_UUID_COLUMN = "target_uuid";
    static final String TARGET_NAME_COLUMN = "target_name";
    static final String OPERATOR_UUID_COLUMN = "operator_uuid";
    static final String OPERATOR_NAME_COLUMN = "operator_name";
    static final String REASON_COLUMN = "reason";
    static final String CREATED_AT_COLUMN = "created_at";
    static final String EXPIRES_AT_COLUMN = "expires_at";
    static final String REVOKED_AT_COLUMN = "revoked_at";

    private static final int IP_CIPHERTEXT_MAX_LENGTH = 255;
    private static final int IP_IV_MAX_LENGTH = 64;
    private static final int IP_HASH_MAX_LENGTH = 64;
    private static final int PLAYER_NAME_MAX_LENGTH = 32;
    private static final int REASON_MAX_LENGTH = 255;

    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    @DatabaseField(columnName = ID_COLUMN, id = true)
    private UUID id;

    @DatabaseField(columnName = IP_CIPHERTEXT_COLUMN, canBeNull = false, width = IP_CIPHERTEXT_MAX_LENGTH)
    private String ipCiphertext;

    @DatabaseField(columnName = IP_IV_COLUMN, canBeNull = false, width = IP_IV_MAX_LENGTH)
    private String ipIv;

    @DatabaseField(columnName = IP_HASH_COLUMN, canBeNull = false, width = IP_HASH_MAX_LENGTH, index = true)
    private String ipHash;

    @DatabaseField(columnName = TARGET_UUID_COLUMN, canBeNull = false)
    private UUID targetUuid;

    @DatabaseField(columnName = TARGET_NAME_COLUMN, canBeNull = false, width = PLAYER_NAME_MAX_LENGTH)
    private String targetName;

    @DatabaseField(columnName = OPERATOR_UUID_COLUMN, canBeNull = false)
    private UUID operatorUuid;

    @DatabaseField(columnName = OPERATOR_NAME_COLUMN, canBeNull = false, width = PLAYER_NAME_MAX_LENGTH)
    private String operatorName;

    @DatabaseField(columnName = REASON_COLUMN, canBeNull = false, width = REASON_MAX_LENGTH)
    private String reason;

    @DatabaseField(columnName = CREATED_AT_COLUMN, canBeNull = false)
    private long createdAt;

    @DatabaseField(columnName = EXPIRES_AT_COLUMN)
    private Long expiresAt;

    @DatabaseField(columnName = REVOKED_AT_COLUMN)
    private Long revokedAt;

    IpPunishmentTable() {}

    EncryptedValue encryptedIp() {
        return new EncryptedValue(
            BASE64_DECODER.decode(this.ipCiphertext),
            BASE64_DECODER.decode(this.ipIv)
        );
    }

    IpPunishment toIpPunishment(String decryptedIp) {
        return IpPunishment.builder()
            .id(this.id)
            .ip(decryptedIp)
            .target(new PunishmentTarget(this.targetUuid, this.targetName))
            .operator(new PunishmentTarget(this.operatorUuid, this.operatorName))
            .reason(this.reason)
            .createdAt(Instant.ofEpochMilli(this.createdAt))
            .expiresAt(toInstant(this.expiresAt))
            .revokedAt(toInstant(this.revokedAt))
            .build();
    }

    static IpPunishmentTable from(IpPunishment ipPunishment, EncryptedValue encryptedIp, String ipHash) {
        IpPunishmentTable table = new IpPunishmentTable();
        table.id = ipPunishment.id();
        table.ipCiphertext = BASE64_ENCODER.encodeToString(encryptedIp.ciphertext());
        table.ipIv = BASE64_ENCODER.encodeToString(encryptedIp.iv());
        table.ipHash = ipHash;
        table.targetUuid = ipPunishment.target().uuid();
        table.targetName = ipPunishment.target().name();
        table.operatorUuid = ipPunishment.operator().uuid();
        table.operatorName = ipPunishment.operator().name();
        table.reason = ipPunishment.reason();
        table.createdAt = ipPunishment.createdAt().toEpochMilli();
        table.expiresAt = ipPunishment.expiresAtOptional().map(Instant::toEpochMilli).orElse(null);
        table.revokedAt = ipPunishment.revokedAtOptional().map(Instant::toEpochMilli).orElse(null);
        return table;
    }

    private static Instant toInstant(Long epochMillis) {
        return epochMillis == null ? null : Instant.ofEpochMilli(epochMillis);
    }
}
