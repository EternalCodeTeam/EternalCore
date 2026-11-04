package com.eternalcode.core.feature.kit.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENKitMessages extends OkaeriConfig implements KitMessages {

    @Comment({ " ", "# Section responsible for listing and checking kits" })
    Notice kitNoneAvailable = Notice.chat("<red>✘ <dark_red>You don't have access to any kits!");

    @Comment({ " ", "# Header for available kits list" })
    Notice kitAvailableListHeader = Notice.chat("<color:#9d6eef>► <white>Available kits:");

    @Comment({ " ", "# Entry for each available kit (Supports MiniMessage click/hover)", "# {KIT} - Name of the kit" })
    Notice kitAvailableListEntry = Notice.chat("  <color:#8c8c8c>• <color:#9d6eef><click:run_command:'/kit {KIT}'><hover:show_text:'<white>Click to receive <color:#9d6eef>{KIT}<white>!'>{KIT}</hover></click>");

    @Comment({ " ", "# Section responsible for receiving kits" })
    @Comment({ " ", "# {KIT} - Name of the kit", "# {PERMISSION} - Required permission" })
    Notice kitNoPermission = Notice.chat("<red>✘ <dark_red>You don't have permission (<red>{PERMISSION}<dark_red>) to use kit <red>{KIT}<dark_red>!");

    @Comment({ " ", "# {KIT} - Name of the kit" })
    Notice kitCooldownActive = Notice.chat("<red>✘ <dark_red>You cannot use kit <red>{KIT} <dark_red>yet! You are on a cooldown.");

    @Comment({ " ", "# {KIT} - Name of the kit" })
    Notice kitReceived = Notice.chat("<color:#9d6eef>► <white>You have successfully received the <color:#9d6eef>{KIT} <white>kit!");

    @Comment({ " ", "# {KIT} - Name of the kit" })
    Notice kitReceiveFailed = Notice.chat("<red>✘ <dark_red>An error occurred while giving you the <red>{KIT} <dark_red>kit!");

    @Comment({ " ", "# Section responsible for giving kits to other players" })
    @Comment({ " ", "# {KIT} - Name of the kit" })
    Notice kitGivenToTargetPlayer = Notice.chat("<color:#9d6eef>► <white>You have successfully given the <color:#9d6eef>{KIT} <white>kit to the player!");

    @Comment({ " ", "# {KIT} - Name of the kit" })
    Notice kitGiveToTargetPlayerFailed = Notice.chat("<red>✘ <dark_red>Failed to give the <red>{KIT} <dark_red>kit to the player!");

    @Comment({ " ", "# Section responsible for kit management and creation" })
    @Comment({ " ", "# {NAME} - Name of the new kit" })
    Notice kitEmptyCreated = Notice.chat("<color:#9d6eef>► <white>Successfully created an empty kit named <color:#9d6eef>{NAME}<white>!");

    Notice kitNameEmpty = Notice.chat("<red>✘ <dark_red>The new kit name cannot be empty!");

    Notice kitNameUnchanged = Notice.chat("<red>✘ <dark_red>The new kit name cannot be the same as the current one!");

    @Comment({ " ", "# {OLD_NAME} - Old name of the kit", "# {NEW_NAME} - New name of the kit" })
    Notice kitRenamed = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{OLD_NAME} <white>has been renamed to <color:#9d6eef>{NEW_NAME}<white>!");

    @Comment({ " ", "# {NAME} - Name of the new kit" })
    Notice kitCreatedFromInventory = Notice.chat("<color:#9d6eef>► <white>Successfully created kit <color:#9d6eef>{NAME} <white>from your inventory!");

    @Comment({ " ", "# {NAME} - Name of the kit" })
    Notice kitCreateFailed = Notice.chat("<red>✘ <dark_red>Failed to create! Kit with the name <red>{NAME} <dark_red>already exists!");

    @Comment({ " ", "# {NAME} - Name of the deleted kit" })
    Notice kitDeleted = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{NAME} <white>has been successfully deleted!");

    @Comment({ " ", "# Section responsible for kit editing GUI" })
    Notice kitEditGuiOpened = Notice.chat("<color:#9d6eef>► <white>Opening kit editor GUI...");

    @Comment({ " ", "# {KIT} - Name of the kit being saved" })
    Notice kitSaved = Notice.chat("<color:#9d6eef>► <white>Successfully saved inventory layout for kit <color:#9d6eef>{KIT}<white>!");
}