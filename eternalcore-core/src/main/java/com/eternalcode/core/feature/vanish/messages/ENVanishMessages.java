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

    public Notice vanishEnabledForOther = Notice.chat("<green>► <white>Enabled vanish mode for <green>{PLAYER}!");
    public Notice vanishDisabledForOther = Notice.chat("<red>► <white>Disabled vanish mode for <green>{PLAYER}!");

    public Notice vanishEnabledByStaff = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>has enabled your vanish mode!");
    public Notice vanishDisabledByStaff = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>has disabled your vanish mode!");

    public Notice currentlyInVanish = Notice.actionbar("<gradient:#9d6eef:#A1AAFF:#9d6eef>You are currently invisible!</gradient> <dark_gray>(<gray>/vanish</gray>)</dark_gray>");

    public Notice joinedInVanish = Notice.chat("<green>► <white>You have joined the server in vanish mode.");
    public Notice playerJoinedInVanish = Notice.chat("<green>► <white>{PLAYER} has joined the server in vanish mode.");

    public Notice cantBlockPlaceWhileVanished = Notice.chat("<red>You cannot place blocks while vanished.");
    public Notice cantBlockBreakWhileVanished = Notice.chat("<red>You cannot break blocks while vanished.");
    public Notice cantUseChatWhileVanished = Notice.chat("<red>You cannot use chat while vanished.");
    public Notice cantDropItemsWhileVanished = Notice.chat("<red>You cannot drop items while vanished.");
    public Notice cantOpenInventoryWhileVanished = Notice.chat("<red>You cannot open your inventory while vanished.");
}
