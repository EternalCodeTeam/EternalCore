package com.eternalcode.core.feature.kit.usage;

import com.eternalcode.core.feature.kit.KitUsage;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.time.Instant;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_kit_usages")
class KitUsageTable {

    static final String PLAYER_COLUMN = "player_id";
    static final String KIT_COLUMN = "kit_name";
    static final String LAST_CLAIMED_AT_COLUMN = "last_claimed_at";

    static final long NEVER_CLAIMED = 0L;

    @DatabaseField(columnName = "id", id = true)
    private String id;

    @DatabaseField(columnName = PLAYER_COLUMN, index = true)
    private UUID playerUniqueId;

    @DatabaseField(columnName = KIT_COLUMN, index = true)
    private String kitName;

    @DatabaseField(columnName = LAST_CLAIMED_AT_COLUMN)
    private long lastClaimedAtMillis;

    @DatabaseField(columnName = "claim_count")
    private int claimCount;

    KitUsageTable() {
    }

    KitUsageTable(UUID playerUniqueId, String kitName, KitUsage usage) {
        this.id = createId(playerUniqueId, kitName);
        this.playerUniqueId = playerUniqueId;
        this.kitName = kitName;
        this.lastClaimedAtMillis = usage.findLastClaimedAt().map(Instant::toEpochMilli).orElse(NEVER_CLAIMED);
        this.claimCount = usage.claimCount();
    }

    static String createId(UUID playerUniqueId, String kitName) {
        return playerUniqueId + ":" + kitName;
    }

    String getKitName() {
        return this.kitName;
    }

    KitUsage toUsage() {
        Instant lastClaimedAt = this.lastClaimedAtMillis == NEVER_CLAIMED ? null : Instant.ofEpochMilli(this.lastClaimedAtMillis);
        return new KitUsage(lastClaimedAt, this.claimCount);
    }
}
