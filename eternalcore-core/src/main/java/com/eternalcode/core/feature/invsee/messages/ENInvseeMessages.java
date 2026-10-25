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

    Notice inspectedPlayerDied = Notice.chat("<red>✘ <dark_red>The player <red>{PLAYER} <dark_red>has died, so you can't inspect his inventory!");

    @Comment("# {PLAYER} - Inspected player")
    String inventoryTitle = "<dark_gray>Inventory of <color:#9d6eef>{PLAYER}";
}
