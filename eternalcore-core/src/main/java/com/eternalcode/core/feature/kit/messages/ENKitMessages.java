package com.eternalcode.core.feature.kit.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENKitMessages extends OkaeriConfig implements KitMessages {

    @Comment("# {KIT} - kit name (displayName), {KIT_NAME} - kit name, {COOLDOWN} - remaining time")
    Notice notFound = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>does not exist!");
    Notice noKits = Notice.chat("<red>✘ <dark_red>There are no kits!");
    Notice noPermission = Notice.chat("<red>✘ <dark_red>You don't have access to kit <red>{KIT_NAME}<dark_red>!");
    Notice onCooldown = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>will be available in <red>{COOLDOWN}<dark_red>!");
    Notice claimed = Notice.chat("<color:#9d6eef>► <white>You claimed kit <color:#9d6eef>{KIT}<white>!");
    Notice claimCancelled = Notice.chat("<red>✘ <dark_red>Could not claim kit <red>{KIT}<dark_red>!");

    @Comment({ " ", "# Kit administration" })
    Notice invalidName = Notice.chat("<red>✘ <dark_red>Invalid kit name <red>{KIT}<dark_red>! Allowed: a-z, 0-9, _ and - (max 32 chars)");
    Notice alreadyExists = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>already exists!");
    Notice created = Notice.chat(
        "<color:#9d6eef>► <white>Created kit <color:#9d6eef>{KIT} <white>(permission: <color:#9d6eef>{PERMISSION}<white>)!",
        "<hover:show_text:'<color:#9d6eef>Set kit items?</color:#9d6eef>'><click:suggest_command:'/kitadmin items {KIT}'><dark_gray>» <gold>/kitadmin items {KIT} <color:#9d6eef>to set items! <gray>(Click)</gray></click></hover>"
    );
    Notice deleted = Notice.chat("<color:#9d6eef>► <white>Deleted kit <color:#9d6eef>{KIT}<white>!");
    Notice itemsEditorOpened = Notice.chat("<color:#9d6eef>► <white>Put the items in and close the inventory to save kit <color:#9d6eef>{KIT}<white>.");
    Notice itemsSaved = Notice.chat("<color:#9d6eef>► <white>Saved <color:#9d6eef>{AMOUNT} <white>items in kit <color:#9d6eef>{KIT}<white>!");
    Notice permissionChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT} <white>permission is now <color:#9d6eef>{PERMISSION}<white>!");
    Notice cooldownChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT} <white>cooldown is now <color:#9d6eef>{COOLDOWN}<white>!");
    Notice emptyHand = Notice.chat("<red>✘ <dark_red>You must hold an item in your hand!");
    Notice iconChanged = Notice.chat("<color:#9d6eef>► <white>Changed icon of kit <color:#9d6eef>{KIT}<white>!");
    Notice invalidSlot = Notice.chat("<red>✘ <dark_red>Slot must be in range <red>0-{MAX}<dark_red>!");
    Notice slotChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT} <white>is now on slot <color:#9d6eef>{SLOT}<white>!");
    Notice displayNameChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT} <white>display name is now <reset>{NAME}<white>!");
    Notice commandAdded = Notice.chat("<color:#9d6eef>► <white>Added command <color:#9d6eef>/{COMMAND} <white>to kit <color:#9d6eef>{KIT}<white>!");
    Notice commandsCleared = Notice.chat("<color:#9d6eef>► <white>Cleared commands of kit <color:#9d6eef>{KIT}<white>!");
    Notice cooldownReset = Notice.chat("<color:#9d6eef>► <white>Reset kit <color:#9d6eef>{KIT} <white>cooldown for <color:#9d6eef>{PLAYER}<white>!");
    Notice databaseError = Notice.chat("<red>✘ <dark_red>A database error occurred, check the console!");
}
