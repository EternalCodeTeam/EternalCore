package com.eternalcode.core.configuration;

import com.eternalcode.core.publish.event.EternalCoreEvent;
import net.dzikoysk.cdn.CdnSettings;

public class ConfigurationSettingsSetupEvent implements EternalCoreEvent {

    private CdnSettings cdnSettings;

    public ConfigurationSettingsSetupEvent(CdnSettings cdnSettings) {
        this.cdnSettings = cdnSettings;
    }

    public CdnSettings getSettings() {
        return cdnSettings;
    }

    public void setSettings(CdnSettings cdnSettings) {
        this.cdnSettings = cdnSettings;
    }

}
