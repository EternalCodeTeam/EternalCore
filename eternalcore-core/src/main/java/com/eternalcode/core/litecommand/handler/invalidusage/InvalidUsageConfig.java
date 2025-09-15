package com.eternalcode.core.litecommand.handler.invalidusage;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
@Getter
@Accessors(fluent = true)
public class InvalidUsageConfig extends OkaeriConfig implements InvalidUsageSettings {

    @Comment({
        "# How to display invalid usage hints:",
        "# - MOST_RELEVANT: show only the most relevant (first) usage (default).",
        "# - DETAILED: show header and a list of top N usages."
    })
    public InvalidUsageHintMode usageHintMode = InvalidUsageHintMode.MOST_RELEVANT;

    @Comment({
        "# When usageHintMode is DETAILED: maximum number of usages to show in the list."
    })
    public int detailedMaxEntries = 5;

    @Comment({
        "# When usageHintMode is DETAILED: whether to show the usage header before the list."
    })
    public boolean detailedShowHeader = true;
}
