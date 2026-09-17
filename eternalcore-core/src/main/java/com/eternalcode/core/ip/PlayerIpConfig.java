package com.eternalcode.core.ip;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PlayerIpConfig extends OkaeriConfig implements PlayerIpSettings {

    @Comment("# Should old login history entries (player IP addresses) be deleted automatically")
    @Comment("# false = keep forever (nothing gets deleted)")
    public boolean retentionEnabled = false;

    @Comment("# How many days of login history to keep, if retentionEnabled = true")
    public int retentionDays = 90;
}
