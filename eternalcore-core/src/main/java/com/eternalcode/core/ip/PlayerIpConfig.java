package com.eternalcode.core.ip;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PlayerIpConfig extends OkaeriConfig implements PlayerIpSettings {

    @Comment("# Czy stare wpisy historii logowań (adresy IP graczy) mają być automatycznie usuwane")
    @Comment("# false = przechowuj bezterminowo (nic nie jest usuwane)")
    public boolean retentionEnabled = false;

    @Comment("# Ile dni historii logowań przechowywać, jeśli retentionEnabled = true")
    public int retentionDays = 90;
}
