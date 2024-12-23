package com.eternalcode.core.configuration.compatibility;

import com.eternalcode.core.configuration.ConfigurationSettingsSetupEvent;
import com.eternalcode.core.configuration.composer.OldEnumComposer;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import io.papermc.lib.PaperLib;

@Controller
public class ConfigurationCompatibilityV21_2 implements Subscriber {

    @Subscribe
    void onConfigSettingsSetup(ConfigurationSettingsSetupEvent event) {
        if (!isEnabled()) {
            return;
        }

        event.getSettings()
            .withDynamicComposer(OldEnumComposer.IS_OLD_ENUM, new OldEnumComposer());
    }

    private static boolean isEnabled() {
        return PaperLib.isVersion(21, 2);
    }

}
