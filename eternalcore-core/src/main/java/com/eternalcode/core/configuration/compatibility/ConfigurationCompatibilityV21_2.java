package com.eternalcode.core.configuration.compatibility;

import com.eternalcode.core.compatibility.Version;
import com.eternalcode.core.configuration.ConfigurationSettingsSetupEvent;
import com.eternalcode.core.configuration.composer.OldEnumComposer;
import com.eternalcode.core.compatibility.Compatibility;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;

@Controller
@Compatibility(from = @Version(minor = 21, patch = 2))
class ConfigurationCompatibilityV21_2 {

    @Subscribe
    void onConfigSettingsSetup(ConfigurationSettingsSetupEvent event) {
        event.getSettings()
            .withDynamicComposer(OldEnumComposer.IS_OLD_ENUM, new OldEnumComposer());
    }

}
