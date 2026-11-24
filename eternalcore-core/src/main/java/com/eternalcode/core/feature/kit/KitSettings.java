package com.eternalcode.core.feature.kit;

public interface KitSettings {

    String defaultPermissionPrefix();

    boolean hideKitsWithoutPermission();

    boolean hideClaimedOneTimeKits();

    KitFullInventoryBehavior fullInventoryBehavior();

    boolean notifyAboutBufferedItems();

    KitConfig.GuiSection gui();

}
