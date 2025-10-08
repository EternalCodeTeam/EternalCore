package com.eternalcode.core.feature.enchant;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class EnchantConfig extends OkaeriConfig implements EnchantSettings {
    @Comment("# Allow unsafe enchantments (enables custom enchants on various items)")
    public boolean unsafeEnchantments = true;
}
