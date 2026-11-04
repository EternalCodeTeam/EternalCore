package com.eternalcode.core.feature.itemedit;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ItemEditConfig extends OkaeriConfig implements ItemEditSettings {
    @Comment("# Blacklist words that cannot be added to the item lore using /ItemLore command. Leave empty if none")
    public List<String> itemLoreBlacklistWords = Arrays.asList(
        "DUPA",
        "KURWA",
        "CHUJ",
        "PIERDOL",
        "SUKA",
        "KUPA",
        "FUCK",
        "SHIT",
        "BITCH",
        "ASS",
        "FAGGOT",
        "BASTARD",
        "KYS"
    );

    @Comment("# Blacklist words that cannot be added to the item lore using /itemname command. Leave empty if none")
    public List<String> itemNameBlacklistWords  = Arrays.asList(
        "DUPA",
        "KURWA",
        "CHUJ",
        "PIERDOL",
        "SUKA",
        "KUPA",
        "FUCK",
        "SHIT",
        "BITCH",
        "ASS",
        "FAGGOT",
        "BASTARD",
        "KYS"
    );
}
