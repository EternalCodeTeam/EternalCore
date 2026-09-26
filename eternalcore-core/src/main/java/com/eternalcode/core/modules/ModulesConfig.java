package com.eternalcode.core.modules;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import java.util.LinkedHashMap;
import java.util.Map;

@Header({
    "#",
    "# This file lets you completely disable individual EternalCore features.",
    "#",
    "# A feature set to 'false' will not register its commands, listeners, tasks or config section at all -",
    "# it behaves as if it was never installed. This only takes effect on plugin startup, not on /reload.",
    "#",
    "# New features are added to this file automatically (enabled by default) as soon as the plugin sees them,",
    "# so you never have to fill this list in by hand - just flip the ones you want off to 'false'.",
    "#",
})
public class ModulesConfig extends OkaeriConfig {

    public Map<String, Boolean> modules = new LinkedHashMap<>();

}
