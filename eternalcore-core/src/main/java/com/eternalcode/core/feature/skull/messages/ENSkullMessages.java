package com.eternalcode.core.feature.skull.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSkullMessages extends OkaeriConfig implements SkullMessages {

    @Comment({" ", "# {SKULL} - Name of the skull owner"})
    public Notice received = Notice.chat("<green>► <white>Player <green>{SKULL} <white>heads received");
}

