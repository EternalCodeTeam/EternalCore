package com.eternalcode.core.feature.container.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLContainerMessages extends OkaeriConfig implements ContainerMessages {

    public Notice anvilOpened = Notice.chat("<green>► <white>Otworzono kowadło!");
    public Notice targetAnvilOpened = Notice.chat("<green>► <white>Otworzono kowadło dla {PLAYER}!");

    public Notice cartographyOpened = Notice.chat("<green>► <white>Otworzono stół kartograficzny!");
    public Notice targetCartographyOpened = Notice.chat("<green>► <white>Otworzono stół kartograficzny dla {PLAYER}!");

    public Notice dispostalOpened = Notice.chat("<green>► <white>Otworzono śmietnik!");
    public Notice targetDispostalOpened = Notice.chat("<green>► <white>Otworzono śmietnik dla {PLAYER}!");

    public Notice enderchestOpened = Notice.chat("<green>► <white>Otworzono enderchest!");
    public Notice targetEnderchestOpened = Notice.chat("<green>► <white>Otworzono enderchest dla {PLAYER}!");

    public Notice grindstoneOpened = Notice.chat("<green>► <white>Otworzono szlifierkę!");
    public Notice targetGrindstoneOpened = Notice.chat("<green>► <white>Otworzono szlifierkę dla {PLAYER}!");

    public Notice loomOpened = Notice.chat("<green>► <white>Otworzono krosno!");
    public Notice targetLoomOpened = Notice.chat("<green>► <white>Otworzono krosno dla {PLAYER}!");

    public Notice smithingOpened = Notice.chat("<green>► <white>Otworzono stół kowalski!");
    public Notice targetSmithingOpened = Notice.chat("<green>► <white>Otworzono stół kowalski dla {PLAYER}!");

    public Notice stonecutterOpened = Notice.chat("<green>► <white>Otworzono piłę kamieniarską!");
    public Notice targetStonecutterOpened = Notice.chat("<green>► <white>Otworzono piłę kamieniarską dla {PLAYER}!");

    public Notice workbenchOpened = Notice.chat("<green>► <white>Otworzono stół rzemieślniczy!");
    public Notice targetWorkbenchOpened = Notice.chat("<green>► <white>Otworzono stół rzemieślniczy dla {PLAYER}!");
}
