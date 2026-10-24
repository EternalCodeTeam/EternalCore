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
    String inventoryTitle = "<dark_gray>Inventory of <color:#9d6eef>{PLAYER}";
}
