package com.eternalcode.core.language;

import com.eternalcode.core.configuration.ConfigWithResource;

import java.util.List;

public interface Messages extends ConfigWithResource {

    Language getLanguage();

    ArgumentSection argument();
    HelpOpSection helpOp();
    AdminChatSection adminChat();
    ChatSection chat();
    TeleportSection teleport();
    OtherMessages other();
    WarpSection warp();

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

    interface HelpOpSection {
        String format();
        String send();
        String coolDown();
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
        String disabledChatInfo();
        String noCommand();
    }

    interface WarpSection {
        String availableList();
        String notExist();
        String noPermission();
        String disabled();
    }

    interface OtherMessages {
        String successfullyReloaded();
        String eternalCoreInfo();
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
        String onlineMessage();
        String listMessage();
        String tposMessage();
        String tposByMessage();
        String nameMessage();
        List<String> whoisCommand();
    }
}
