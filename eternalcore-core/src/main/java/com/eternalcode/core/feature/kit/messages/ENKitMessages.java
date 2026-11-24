package com.eternalcode.core.feature.kit.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENKitMessages extends OkaeriConfig implements KitMessages {

    @Comment({
        "# {KIT} - kit display name, {KIT_NAME} - kit name, {COOLDOWN} - remaining time,",
        "# {PLAYTIME} - missing playtime, {WORLDS} - required worlds, {GAME_MODES} - required game modes, {AMOUNT} - number of items"
    })
    Notice notFound = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>does not exist!");
    Notice noKits = Notice.chat("<red>✘ <dark_red>There are no available kits!");
    Notice claimed = Notice.chat("<color:#9d6eef>► <white>You claimed kit <color:#9d6eef>{KIT}<white>!");
    Notice claimedToBuffer = Notice.chat(
        "<color:#9d6eef>► <white>You claimed kit <color:#9d6eef>{KIT}<white>, but <color:#9d6eef>{AMOUNT} <white>items did not fit into your inventory.",
        "<hover:show_text:'<color:#9d6eef>Claim stored items?</color:#9d6eef>'><click:suggest_command:'/kit buffer'><dark_gray>» <gold>/kit buffer <color:#9d6eef>to claim them! <gray>(Click)</gray></click></hover>"
    );
    Notice noPermission = Notice.chat("<red>✘ <dark_red>You don't have access to kit <red>{KIT}<dark_red>!");
    Notice onCooldown = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>will be available in <red>{COOLDOWN}<dark_red>!");
    Notice alreadyClaimed = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>can be claimed only once and you already claimed it!");
    Notice wrongWorld = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>can be claimed only in worlds: <red>{WORLDS}<dark_red>!");
    Notice wrongGameMode = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>can be claimed only in game modes: <red>{GAME_MODES}<dark_red>!");
    Notice notEnoughPlaytime = Notice.chat("<red>✘ <dark_red>You need <red>{PLAYTIME} <dark_red>more playtime to claim kit <red>{KIT}<dark_red>!");
    Notice inventoryFull = Notice.chat("<red>✘ <dark_red>Make room in your inventory to claim kit <red>{KIT}<dark_red>!");
    Notice claimCancelled = Notice.chat("<red>✘ <dark_red>Could not claim kit <red>{KIT}<dark_red>!");
    Notice dataNotLoaded = Notice.chat("<red>✘ <dark_red>Your kit data is still loading, try again in a moment!");

    @Comment("# {TYPE} - type, {STATUS} - status, {ITEMS} - number of items, {CLAIMS} - times claimed, {DESCRIPTION} - description")
    Notice info = Notice.chat(
        "<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT} <dark_gray>(<gray>{KIT_NAME}<dark_gray>)",
        "{DESCRIPTION}",
        "<dark_gray>» <gray>Type: <color:#9d6eef>{TYPE}",
        "<dark_gray>» <gray>Cooldown: <color:#9d6eef>{COOLDOWN}",
        "<dark_gray>» <gray>Items: <color:#9d6eef>{ITEMS}",
        "<dark_gray>» <gray>Worlds: <color:#9d6eef>{WORLDS} <dark_gray>| <gray>Game modes: <color:#9d6eef>{GAME_MODES}",
        "<dark_gray>» <gray>Required playtime: <color:#9d6eef>{PLAYTIME}",
        "<dark_gray>» <gray>Times claimed: <color:#9d6eef>{CLAIMS}",
        "<dark_gray>» <gray>Status: {STATUS}"
    );

    @Comment({ " ", "# Buffer for items that did not fit into the inventory" })
    Notice bufferEmpty = Notice.chat("<red>✘ <dark_red>You don't have any stored kit items!");
    Notice bufferClaimed = Notice.chat("<color:#9d6eef>► <white>Claimed <color:#9d6eef>{AMOUNT} <white>items from your kit buffer, left in the buffer: <color:#9d6eef>{REMAINING}<white>.");
    Notice bufferWaiting = Notice.chat(
        "<color:#9d6eef>► <white>You have <color:#9d6eef>{AMOUNT} <white>items waiting in your kit buffer!",
        "<hover:show_text:'<color:#9d6eef>Claim stored items?</color:#9d6eef>'><click:suggest_command:'/kit buffer'><dark_gray>» <gold>/kit buffer <color:#9d6eef>to claim them! <gray>(Click)</gray></click></hover>"
    );

    @Comment({ " ", "# Kit administration, {PLAYER} - player" })
    Notice given = Notice.chat("<color:#9d6eef>► <white>Gave kit <color:#9d6eef>{KIT} <white>to <color:#9d6eef>{PLAYER}<white>!");
    Notice received = Notice.chat("<color:#9d6eef>► <white>You received kit <color:#9d6eef>{KIT}<white>!");
    Notice usageReset = Notice.chat("<color:#9d6eef>► <white>Reset kit <color:#9d6eef>{KIT} <white>cooldown for <color:#9d6eef>{PLAYER}<white>!");
    Notice invalidName = Notice.chat("<red>✘ <dark_red>Invalid kit name <red>{KIT_NAME}<dark_red>! Allowed: a-z, 0-9, _ and - (max 32 chars)");
    Notice reservedName = Notice.chat("<red>✘ <dark_red>Name <red>{KIT_NAME} <dark_red>is reserved for /kit subcommands!");
    Notice alreadyExists = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>already exists!");
    Notice created = Notice.chat(
        "<color:#9d6eef>► <white>Created kit <color:#9d6eef>{KIT_NAME} <white>(permission: <color:#9d6eef>{PERMISSION}<white>)!",
        "<hover:show_text:'<color:#9d6eef>Set kit items?</color:#9d6eef>'><click:suggest_command:'/kitadmin items {KIT_NAME}'><dark_gray>» <gold>/kitadmin items {KIT_NAME} <color:#9d6eef>to set items! <gray>(Click)</gray></click></hover>"
    );
    Notice deleted = Notice.chat("<color:#9d6eef>► <white>Deleted kit <color:#9d6eef>{KIT_NAME}<white>!");
    Notice itemsEditorOpened = Notice.chat("<color:#9d6eef>► <white>Put the items in and close the inventory to save kit <color:#9d6eef>{KIT_NAME}<white>.");
    Notice itemsSaved = Notice.chat("<color:#9d6eef>► <white>Saved <color:#9d6eef>{AMOUNT} <white>items in kit <color:#9d6eef>{KIT_NAME}<white>!");
    Notice permissionChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT_NAME} <white>permission is now <color:#9d6eef>{PERMISSION}<white>!");
    Notice cooldownChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT_NAME} <white>cooldown is now <color:#9d6eef>{COOLDOWN}<white>!");
    Notice typeChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT_NAME} <white>type is now <color:#9d6eef>{TYPE}<white>!");
    Notice emptyHand = Notice.chat("<red>✘ <dark_red>You must hold an item in your hand!");
    Notice iconChanged = Notice.chat("<color:#9d6eef>► <white>Changed icon of kit <color:#9d6eef>{KIT_NAME}<white>!");
    Notice invalidSlot = Notice.chat("<red>✘ <dark_red>Slot must be in range <red>0-{MAX}<dark_red>!");
    Notice slotChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT_NAME} <white>is now on slot <color:#9d6eef>{SLOT}<white>!");
    Notice displayNameChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT_NAME} <white>display name is now <reset>{KIT}<white>!");
    Notice commandAdded = Notice.chat("<color:#9d6eef>► <white>Added command <color:#9d6eef>/{COMMAND} <white>to kit <color:#9d6eef>{KIT_NAME}<white>!");
    Notice commandsCleared = Notice.chat("<color:#9d6eef>► <white>Cleared commands of kit <color:#9d6eef>{KIT_NAME}<white>!");

    @Comment("# {COMMANDS} - list of commands formatted with commands-list-entry, {INDEX} - command number")
    Notice commandsList = Notice.chat(
        "<color:#9d6eef>► <white>Commands executed after claiming kit <color:#9d6eef>{KIT_NAME}<white>:",
        "{COMMANDS}"
    );
    String commandsListEntry = "<dark_gray>{INDEX}. <gray>/{COMMAND}";
    Notice commandsEmpty = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>has no commands!");
    Notice commandRemoved = Notice.chat("<color:#9d6eef>► <white>Removed command <color:#9d6eef>/{COMMAND} <white>from kit <color:#9d6eef>{KIT_NAME}<white>!");
    Notice invalidCommandNumber = Notice.chat("<red>✘ <dark_red>Command number must be in range <red>1-{MAX}<dark_red>! Check: <red>/kitadmin commands list {KIT_NAME}");

    Notice descriptionLineAdded = Notice.chat("<color:#9d6eef>► <white>Added description line <reset>{LINE} <white>to kit <color:#9d6eef>{KIT_NAME}<white>!");
    Notice descriptionCleared = Notice.chat("<color:#9d6eef>► <white>Cleared description of kit <color:#9d6eef>{KIT_NAME}<white>!");

    @Comment("# {LINES} - description lines formatted with description-list-entry, {INDEX} - line number, {LINE} - line")
    Notice descriptionList = Notice.chat(
        "<color:#9d6eef>► <white>Description of kit <color:#9d6eef>{KIT_NAME}<white>:",
        "{LINES}"
    );
    String descriptionListEntry = "<dark_gray>{INDEX}. <reset>{LINE}";
    Notice descriptionEmpty = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>has no description!");
    Notice descriptionLineRemoved = Notice.chat("<color:#9d6eef>► <white>Removed description line <reset>{LINE} <white>from kit <color:#9d6eef>{KIT_NAME}<white>!");
    Notice invalidDescriptionLineNumber = Notice.chat("<red>✘ <dark_red>Line number must be in range <red>1-{MAX}<dark_red>! Check: <red>/kitadmin description list {KIT_NAME}");

    @Comment({ " ", "# /kit GUI texts" })
    public ENKitGuiMessages gui = new ENKitGuiMessages();

    @Getter
    @Accessors(fluent = true)
    public static class ENKitGuiMessages extends OkaeriConfig implements KitGuiMessages {

        @Comment("# {KIT} - kit display name")
        String title = "<dark_gray>» <color:#9d6eef>Kits";
        String previewTitle = "<dark_gray>» <color:#9d6eef>Kit <white>{KIT}";
        String editorTitle = "<dark_gray>» <color:#9d6eef>Editing kit: <white>{KIT}";

        @Comment({ "# Lines added below the kit description", "# {TYPE} - type, {KIT_COOLDOWN} - kit cooldown, {CLAIMS} - times claimed, {STATUS} - status" })
        List<String> kitLore = List.of(
            "",
            "<gray>Type: <color:#9d6eef>{TYPE}",
            "<gray>Cooldown: <color:#9d6eef>{KIT_COOLDOWN}",
            "{STATUS}",
            ""
        );
        String claimHint = "<dark_gray>» <gray>LMB <white>to claim, <gray>RMB <white>to preview";
        String previewHint = "<dark_gray>» <gray>RMB <white>to preview";

        String claimButtonName = "<color:#9d6eef>► <white>Claim kit";
        List<String> claimButtonLore = List.of("<dark_gray>» <gray>Click to claim this kit");
        String backButtonName = "<dark_gray>« <white>Back";
        List<String> backButtonLore = List.of("<dark_gray>» <gray>Click to return to the kit list");

        String typeRecurring = "Recurring";
        String typeOneTime = "One-time";
        String typeFirstJoin = "Starter";
        String none = "none";
        String any = "any";

        @Comment("# {COOLDOWN} - remaining time, {PLAYTIME} - missing playtime")
        String statusAvailable = "<color:#9d6eef>► <white>Available to claim!";
        String statusOnCooldown = "<red>✘ <dark_red>Available in: <red>{COOLDOWN}";
        String statusAlreadyClaimed = "<red>✘ <dark_red>Already claimed";
        String statusNoPermission = "<red>✘ <dark_red>You don't have access to this kit!";
        String statusWrongWorld = "<red>✘ <dark_red>Not available in this world";
        String statusWrongGameMode = "<red>✘ <dark_red>Not available in this game mode";
        String statusNotEnoughPlaytime = "<red>✘ <dark_red>You need <red>{PLAYTIME} <dark_red>more playtime";
        String statusInventoryFull = "<red>✘ <dark_red>Not enough inventory space";
        String statusDataNotLoaded = "<red>✘ <dark_red>Loading data...";
    }
}
