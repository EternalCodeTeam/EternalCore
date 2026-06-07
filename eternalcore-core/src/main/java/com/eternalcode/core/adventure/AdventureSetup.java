package com.eternalcode.core.adventure;

import com.eternalcode.commons.adventure.AdventureLegacyColorPostProcessor;
import com.eternalcode.commons.adventure.AdventureLegacyColorPreProcessor;
import com.eternalcode.commons.adventure.AdventureUrlPostProcessor;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import net.kyori.adventure.text.minimessage.MiniMessage;

@Setup
class AdventureSetup {

    @Bean
    MiniMessage miniMessage() {
        return MiniMessage.builder()
            .postProcessor(new AdventureUrlPostProcessor())
            .postProcessor(new AdventureLegacyColorPostProcessor())
            .preProcessor(new AdventureLegacyColorPreProcessor())
            .build();
    }

}
