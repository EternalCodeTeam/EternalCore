package com.eternalcode.core.configuration.compatibility;

import com.eternalcode.core.compatibility.Version;
import com.eternalcode.core.configuration.ConfigurationSerdesSetupEvent;
import com.eternalcode.core.configuration.serializer.OldEnumSerializer;
import com.eternalcode.core.compatibility.Compatibility;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;

// TODO: Make this package private
@Controller
@Compatibility(from = @Version(minor = 21, patch = 2))
public class ConfigurationCompatibilityV21_2 {

    @Subscribe
    void onConfigSerdesSetup(ConfigurationSerdesSetupEvent event) {
        event.registry().register(new OldEnumSerializer());
    }

}
