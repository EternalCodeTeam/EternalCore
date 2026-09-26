package com.eternalcode.core.feature.kit;

public interface KitSettings {

    String defaultPermissionPrefix();

    boolean hideKitsWithoutPermission();

    KitConfig.GuiSection gui();

}
