package com.eternalcode.core.feature.near;

import eu.okaeri.configs.OkaeriConfig;

public class NearConfig extends OkaeriConfig implements NearSettings {

    public String defaultBulletpointStyle = "<gray>";
    public String defaultBulletpointSymbol = "-";
    public String defaultListItemStyle = "<white>";

    @Override
    public String bulletPointStyle() {
        return this.defaultBulletpointStyle;
    }

    @Override
    public String bulletPointSymbol() {
        return this.defaultBulletpointSymbol;
    }

    @Override
    public String listItemStyle() {
        return this.defaultListItemStyle;
    }
}
