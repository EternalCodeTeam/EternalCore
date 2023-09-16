package com.eternalcode.core.feature.reportchat;

import com.eternalcode.core.delay.DelaySettings;
import java.time.Duration;

public interface HelpOpSettings extends DelaySettings {

    Duration getHelpOpDelay();

    @Override
    default Duration delay() {
        return this.getHelpOpDelay();
    }

}
