package com.eternalcode.core.feature.kit.cooldown;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;
import java.util.UUID;

/**
 * ORMLite has no composite keys, so the id is "playerUuid:kitName".
 */
@DatabaseTable(tableName = "eternal_core_kit_cooldowns")
class KitCooldownTable {

    static final String PLAYER_COLUMN = "player_id";
    static final String KIT_COLUMN = "kit_name";
    static final String EXPIRES_AT_COLUMN = "expires_at";

    private static final String ID_SEPARATOR = ":";

    @DatabaseField(columnName = "id", id = true)
    private String id;

    @DatabaseField(columnName = PLAYER_COLUMN, index = true)
    private UUID playerUniqueId;

    @DatabaseField(columnName = KIT_COLUMN, index = true)
    private String kitName;

    @DatabaseField(columnName = EXPIRES_AT_COLUMN)
    private long expiresAtMillis;

    KitCooldownTable() {
    }

    KitCooldownTable(UUID playerUniqueId, String kitName, Instant expiresAt) {
        this.id = createId(playerUniqueId, kitName);
        this.playerUniqueId = playerUniqueId;
        this.kitName = kitName;
        this.expiresAtMillis = expiresAt.toEpochMilli();
    }

    static String createId(UUID playerUniqueId, String kitName) {
        return playerUniqueId + ID_SEPARATOR + kitName;
    }

    String getKitName() {
        return this.kitName;
    }

    Instant getExpiresAt() {
        return Instant.ofEpochMilli(this.expiresAtMillis);
    }
}
