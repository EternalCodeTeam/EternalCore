package com.eternalcode.core.ip;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_player_ips")
class PlayerIpTable {

    static final String ID_COLUMN = "id";
    static final String IP_CIPHERTEXT_COLUMN = "ip_ciphertext";
    static final String IP_IV_COLUMN = "ip_iv";
    static final String IP_HASH_COLUMN = "ip_hash";
    static final String TARGET_UUID_COLUMN = "target_uuid";
    static final String TARGET_NAME_COLUMN = "target_name";
    static final String FIRST_SEEN_COLUMN = "first_seen";
    static final String LAST_SEEN_COLUMN = "last_seen";

    private static final int IP_CIPHERTEXT_MAX_LENGTH = 255;
    private static final int IP_IV_MAX_LENGTH = 64;
    private static final int IP_HASH_MAX_LENGTH = 64;
    private static final int PLAYER_NAME_MAX_LENGTH = 16;

    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    @DatabaseField(columnName = ID_COLUMN, id = true)
    private UUID id;

    @DatabaseField(columnName = IP_CIPHERTEXT_COLUMN, canBeNull = false, width = IP_CIPHERTEXT_MAX_LENGTH)
    private String ipCiphertext;

    @DatabaseField(columnName = IP_IV_COLUMN, canBeNull = false, width = IP_IV_MAX_LENGTH)
    private String ipIv;

    @DatabaseField(columnName = IP_HASH_COLUMN, canBeNull = false, width = IP_HASH_MAX_LENGTH, index = true, uniqueCombo = true)
    private String ipHash;

    @DatabaseField(columnName = TARGET_UUID_COLUMN, canBeNull = false, index = true, uniqueCombo = true)
    private UUID targetUuid;

    @DatabaseField(columnName = TARGET_NAME_COLUMN, canBeNull = false, width = PLAYER_NAME_MAX_LENGTH)
    private String targetName;

    @DatabaseField(columnName = FIRST_SEEN_COLUMN, canBeNull = false)
    private long firstSeen;

    @DatabaseField(columnName = LAST_SEEN_COLUMN, canBeNull = false, index = true)
    private long lastSeen;

    PlayerIpTable() {}

    static PlayerIpTable create(
        UUID targetUuid,
        String targetName,
        EncryptedValue encryptedIp,
        String ipHash,
        long seenAtEpochMillis
    ) {
        PlayerIpTable table = new PlayerIpTable();
        table.id = UUID.randomUUID();
        table.ipCiphertext = BASE64_ENCODER.encodeToString(encryptedIp.ciphertext());
        table.ipIv = BASE64_ENCODER.encodeToString(encryptedIp.iv());
        table.ipHash = ipHash;
        table.targetUuid = targetUuid;
        table.targetName = targetName;
        table.firstSeen = seenAtEpochMillis;
        table.lastSeen = seenAtEpochMillis;
        return table;
    }

    void markSeen(String targetName, long seenAtEpochMillis) {
        this.targetName = targetName;
        this.lastSeen = seenAtEpochMillis;
    }

    EncryptedValue encryptedIp() {
        return new EncryptedValue(
            BASE64_DECODER.decode(this.ipCiphertext),
            BASE64_DECODER.decode(this.ipIv)
        );
    }

    PlayerIpEntry toEntry(String decryptedIp) {
        return new PlayerIpEntry(
            this.id,
            this.targetUuid,
            this.targetName,
            decryptedIp,
            Instant.ofEpochMilli(this.firstSeen),
            Instant.ofEpochMilli(this.lastSeen)
        );
    }
}
