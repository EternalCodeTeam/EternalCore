package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.language.LanguageSelector;
import com.eternalcode.core.configuration.AbstractConfigWithResource;

import java.io.File;

public class InventoryConfiguration extends AbstractConfigWithResource {

    public InventoryConfiguration(File folder, String child) {
        super(folder, child);
    }

    public LanguageSelector languageSelector = new LanguageSelector();
}
