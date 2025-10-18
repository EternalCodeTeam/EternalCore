package com.eternalcode.core.feature.jail;

import java.time.Duration;
import java.util.Set;

public interface JailSettings {

    Duration defaultJailDuration();

    JailCommandRestrictionType restrictionType();

    Set<String> restrictedCommands();
}
