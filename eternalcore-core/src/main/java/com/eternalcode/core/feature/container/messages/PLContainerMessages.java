package com.eternalcode.core.feature.container.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLContainerMessages extends OkaeriConfig implements ContainerMessages {

    Notice anvilOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono kowadło!");
    Notice targetAnvilOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono kowadło dla {PLAYER}!");

    Notice cartographyOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono stół kartograficzny!");
    Notice targetCartographyOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono stół kartograficzny dla {PLAYER}!");

    Notice enderchestOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono skrzynię kresu!");
    Notice targetEnderchestOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono skrzynię kresu dla {PLAYER}!");

    Notice grindstoneOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono szlifierkę!");
    Notice targetGrindstoneOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono szlifierkę dla {PLAYER}!");

    Notice loomOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono krosno!");
    Notice targetLoomOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono krosno dla {PLAYER}!");

    Notice smithingOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono stół kowalski!");
    Notice targetSmithingOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono stół kowalski dla {PLAYER}!");

    Notice stonecutterOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono piłę kamieniarską!");
    Notice targetStonecutterOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono piłę kamieniarską dla {PLAYER}!");

    Notice workbenchOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono stół rzemieślniczy!");
    Notice targetWorkbenchOpened = Notice.chat("<color:#9d6eef>► <white>Otworzono stół rzemieślniczy dla {PLAYER}!");
}
