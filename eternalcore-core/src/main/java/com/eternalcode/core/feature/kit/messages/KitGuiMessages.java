package com.eternalcode.core.feature.kit.messages;

import java.util.List;

public interface KitGuiMessages {

    String title();
    String previewTitle();
    String editorTitle();

    List<String> kitLore();
    String claimHint();
    String previewHint();

    String claimButtonName();
    List<String> claimButtonLore();
    String backButtonName();
    List<String> backButtonLore();

    String typeRecurring();
    String typeOneTime();
    String typeFirstJoin();
    String none();
    String any();

    String statusAvailable();
    String statusOnCooldown();
    String statusAlreadyClaimed();
    String statusNoPermission();
    String statusWrongWorld();
    String statusWrongGameMode();
    String statusNotEnoughPlaytime();
    String statusInventoryFull();
    String statusDataNotLoaded();

}
