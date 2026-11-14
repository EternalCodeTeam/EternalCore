package com.eternalcode.core.feature.enderchest.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

@Getter
@Accessors(fluent = true)
public class ENEnderchestMessages extends OkaeriConfig implements EnderchestMessages {

    Notice openedEnderchest = Notice.chat("<color:#9d6eef>► <white>Ender chest opened!");
    Notice customEnderchestDisabled = Notice.chat("<red>✘ <dark_red>Custom ender chests are disabled on this server!");
    Notice enderchestsBlocked = Notice.chat("<red>✘ <dark_red>Ender chests are disabled on this server!");

    @Comment({ " ", "# {PAGE} - Page number, {PAGES} - Number of available pages" })
    Notice openedEnderchestPage = BukkitNotice.builder()
        .actionBar("<color:#9d6eef>► <white>Ender chest page <color:#9d6eef>{PAGE}<white>/<color:#9d6eef>{PAGES}")
        .sound(Sound.BLOCK_ENDER_CHEST_OPEN, 0.5f, 1f)
        .build();

    Notice enderchestPageUnavailable = Notice.chat("<red>✘ <dark_red>Page <red>{PAGE} <dark_red>is not available! You can use <red>{PAGES} <dark_red>page(s).");

    @Comment({ " ", "# {TIME} - Time left before the next page switch" })
    Notice enderchestPageSwitchDelay = Notice.chat("<red>✘ <dark_red>You can switch the page in: <red>{TIME}");

    @Comment({ " ", "# {PLAYER} - Ender chest owner, {PAGE} - Page number, {PAGES} - Number of available pages" })
    Notice openedTargetPlayerEnderchest = Notice.chat("<color:#9d6eef>► <white>Opened page <color:#9d6eef>{PAGE} <white>of {PLAYER}'s ender chest!");

    @Comment({ " ", "# {PLAYER} - Ender chest owner" })
    Notice playerEnderchestEmpty = Notice.chat("<red>✘ <dark_red>Player <red>{PLAYER} <dark_red>has nothing stored! "
        + "If they have not joined since the custom ender chests were enabled, their items are still in the vanilla one.");
    Notice enderchestInUse = Notice.chat("<red>✘ <dark_red>The ender chest of <red>{PLAYER} <dark_red>is open somewhere else, try again in a moment!");
}
