package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.core.delay.DelaySettings;

import java.time.Duration;

public interface RepairSettings extends DelaySettings {

    Duration getRepairDelay();

    @Override
    default Duration delay() {
        return this.getRepairDelay();
    }

}
