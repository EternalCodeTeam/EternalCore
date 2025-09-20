package com.eternalcode.core.litecommand.handler.invalidusage;

public interface InvalidUsageSettings {
    InvalidUsageHintMode usageHintMode();

    int detailedMaxEntries();

    boolean detailedShowHeader();
}
