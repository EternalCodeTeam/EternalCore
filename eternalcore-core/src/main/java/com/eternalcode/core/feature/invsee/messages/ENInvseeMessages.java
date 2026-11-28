package com.eternalcode.core.feature.invsee.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENInvseeMessages extends OkaeriConfig implements InvseeMessages {
    Notice cannotInspectSelf = Notice.chat("<red>✘ <dark_red>You can't inspect your inventory!");

    @Comment("# {PLAYER} - Inspected player")
    Notice inspectedPlayerDied = Notice.chat("<red>✘ <dark_red>The player <red>{PLAYER} <dark_red>has died, so you can't inspect his inventory!");

    @Comment("# {PLAYER} - Inspected player")
    String inventoryTitle = "<dark_gray>Inventory of <color:#9d6eef>{PLAYER}";

    @Comment("# Names of the items marking which item is placed in the slot below them")
    String helmetSlotName = "<color:#9d6eef>↓ <white>Helmet <color:#9d6eef>↓";
    String chestplateSlotName = "<color:#9d6eef>↓ <white>Chestplate <color:#9d6eef>↓";
    String leggingsSlotName = "<color:#9d6eef>↓ <white>Leggings <color:#9d6eef>↓";
    String bootsSlotName = "<color:#9d6eef>↓ <white>Boots <color:#9d6eef>↓";
    String offhandSlotName = "<color:#9d6eef>↓ <white>Offhand <color:#9d6eef>↓";
}
