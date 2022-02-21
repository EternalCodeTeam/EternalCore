package com.eternalcode.core.configuration.lang;

import java.util.List;

public interface MessagesConfiguration {

    String getLang();

    ArgumentSection getArgumentSection();
    HelpopSection getHelpOpSection();
    AdminChatSection getAdminChatSection();
    ChatSection getChatSection();
    TeleportSection getTeleportSection();
    OtherMessages getOtherMessages();

    interface ArgumentSection {
        String permissionMessage();
        String offlinePlayer();
        String onlyPlayer();
        String notNumber();
        String numberBiggerThanOrEqualZero();
        String noItem();
        String noMaterial();
        String noArgument();
    }

    interface HelpopSection {
        String format();
        String send();
    }

    interface AdminChatSection {
        String format();
    }

    interface TeleportSection {
        String actionBarMessage();
        String cancel();
        String teleported();
        String teleporting();
        String haveTeleport();
    }

    interface ChatSection {
        String disabled();
        String enabled();
        String cleared();
        String alreadyDisabled();
        String alreadyEnabled();
        String slowModeSet();
        String slowMode();
        String disable();
        String noCommand();
    }

    interface OtherMessages {
        String successfullyReloaded();
        String successfullyTeleported();
        String successfullyTeleportedPlayer();
        String alertMessagePrefix();
        String clearMessage();
        String clearByMessage();
        String disposalTitle();
        String foodMessage();
        String foodOtherMessage();
        String healMessage();
        String healedMessage();
        String nullHatMessage();
        String repairMessage();
        String skullMessage();
        String killSelf();
        String killedMessage();
        String speedBetweenZeroAndTen();
        String speedSet();
        String speedSetBy();
        String godMessage();
        String godSetMessage();
        String flyMessage();
        String flySetMessage();
        String giveReceived();
        String giveGiven();
        String spawnSet();
        String spawnNoSet();
        String spawnTeleportedBy();
        String spawnTeleportedOther();
        String gameModeNotCorrect();
        String gameModeMessage();
        String gameModeSetMessage();
        String pingMessage();
        String pingOtherMessage();
        List<String> whoisCommand();
    }
}
