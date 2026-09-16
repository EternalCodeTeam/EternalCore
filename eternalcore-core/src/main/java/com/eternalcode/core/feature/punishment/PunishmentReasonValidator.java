package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.util.Objects;

@Service
public class PunishmentReasonValidator {

    private final PunishmentSettings punishmentSettings;

    @Inject
    PunishmentReasonValidator(PunishmentSettings punishmentSettings) {
        this.punishmentSettings = punishmentSettings;
    }

    public boolean isValid(String reason) {
        Objects.requireNonNull(reason, "reason cannot be null");

        if (!this.punishmentSettings.reasonLegthEnabled()) {
            return true;
        }

        int length = reason.length();
        return length >= this.punishmentSettings.minReasonLength() && length <= this.punishmentSettings.maxReasonLength();
    }
}
