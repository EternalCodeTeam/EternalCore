package com.eternalcode.core.util.date;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class DateConfig extends OkaeriConfig implements DateSettings {

    @Comment({
            "# Date format used in the plugin",
            "# You can use standard Java date format patterns",
            "# Examples:",
            "# yyyy-MM-dd HH:mm:ss - 2024-12-06 14:30:00",
            "# dd.MM.yyyy HH:mm - 06.12.2024 14:30"
    })
    public String format = "yyyy-MM-dd HH:mm:ss";

}
