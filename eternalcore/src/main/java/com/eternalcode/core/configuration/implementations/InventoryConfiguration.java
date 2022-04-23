package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.configuration.implementations.inventories.LanguageSelector;
import com.eternalcode.core.configuration.resource.AbstractConfigResource;

import java.io.File;

public class InventoryConfiguration extends AbstractConfigResource {

    public InventoryConfiguration(File folder, String child) {
        super(folder, child);
    }

    public LanguageSelector languageSelector = new LanguageSelector();
}
