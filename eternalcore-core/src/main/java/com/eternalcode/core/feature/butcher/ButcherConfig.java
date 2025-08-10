package com.eternalcode.core.feature.butcher;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ButcherConfig extends OkaeriConfig implements ButcherSettings {
    @Comment("# Safe number of chunks for command execution (above this number it will not be possible to execute the command)")
    public int safeChunkNumber = 5;
}
