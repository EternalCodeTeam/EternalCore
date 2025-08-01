package com.eternalcode.core.feature.vanish.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENVanishMessages extends OkaeriConfig implements VanishMessages {

    public Notice vanishEnabled = Notice.chat("<green>► <white>Vanish mode enabled!");
    public Notice vanishDisabled = Notice.chat("<red>► <white>Vanish mode disabled!");

    public Notice vanishEnabledOther = Notice.chat("<green>► <white>{PLAYER} has enabled vanish mode!");
    public Notice vanishDisabledOther = Notice.chat("<red>► <white>{PLAYER} has disabled vanish mode!");

    public Notice currentlyInVanish = Notice.actionbar("<green>► <aqua>You are now vanished!");

    public Notice joinedInVanish = Notice.chat("<green>► <white>You have joined the server in vanish mode.");
    public Notice playerJoinedInVanish = Notice.chat("<green>► <white>{PLAYER} has joined the server in vanish mode.");

    public Notice cantBlockPlaceWhileVanished = Notice.chat("<red>You cannot place blocks while vanished.");
    public Notice cantBlockBreakWhileVanished = Notice.chat("<red>You cannot break blocks while vanished.");
    public Notice cantUseChatWhileVanished = Notice.chat("<red>You cannot use chat while vanished.");
    public Notice cantDropItemsWhileVanished = Notice.chat("<red>You cannot drop items while vanished.");
    public Notice cantPickupItemsWhileVanished = Notice.chat("<red>You cannot pick up items while vanished.");
    public Notice cantOpenInventoryWhileVanished = Notice.chat("<red>You cannot open your inventory while vanished.");

}
