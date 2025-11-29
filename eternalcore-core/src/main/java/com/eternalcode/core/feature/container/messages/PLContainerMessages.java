package com.eternalcode.core.feature.container.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLContainerMessages extends OkaeriConfig implements ContainerMessages {

    Notice anvilOpened = Notice.chat("<green>► <white>Otworzono kowadło!");
    Notice targetAnvilOpened = Notice.chat("<green>► <white>Otworzono kowadło dla {PLAYER}!");

    Notice cartographyOpened = Notice.chat("<green>► <white>Otworzono stół kartograficzny!");
    Notice targetCartographyOpened = Notice.chat("<green>► <white>Otworzono stół kartograficzny dla {PLAYER}!");

    Notice enderchestOpened = Notice.chat("<green>► <white>Otworzono enderchest!");
    Notice targetEnderchestOpened = Notice.chat("<green>► <white>Otworzono enderchest dla {PLAYER}!");

    Notice grindstoneOpened = Notice.chat("<green>► <white>Otworzono szlifierkę!");
    Notice targetGrindstoneOpened = Notice.chat("<green>► <white>Otworzono szlifierkę dla {PLAYER}!");

    Notice loomOpened = Notice.chat("<green>► <white>Otworzono krosno!");
    Notice targetLoomOpened = Notice.chat("<green>► <white>Otworzono krosno dla {PLAYER}!");

    Notice smithingOpened = Notice.chat("<green>► <white>Otworzono stół kowalski!");
    Notice targetSmithingOpened = Notice.chat("<green>► <white>Otworzono stół kowalski dla {PLAYER}!");

    Notice stonecutterOpened = Notice.chat("<green>► <white>Otworzono piłę kamieniarską!");
    Notice targetStonecutterOpened = Notice.chat("<green>► <white>Otworzono piłę kamieniarską dla {PLAYER}!");

    Notice workbenchOpened = Notice.chat("<green>► <white>Otworzono stół rzemieślniczy!");
    Notice targetWorkbenchOpened = Notice.chat("<green>► <white>Otworzono stół rzemieślniczy dla {PLAYER}!");
}
