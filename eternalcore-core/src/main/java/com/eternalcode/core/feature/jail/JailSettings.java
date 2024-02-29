package com.eternalcode.core.feature.jail;

import java.time.Duration;
import java.util.List;

public interface JailSettings {

    Duration defaultJailDuration();

    List<String> allowedCommands();
}
