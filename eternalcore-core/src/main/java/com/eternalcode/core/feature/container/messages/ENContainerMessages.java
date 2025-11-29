package com.eternalcode.core.feature.container.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENContainerMessages extends OkaeriConfig implements ContainerMessages {

    Notice anvilOpened = Notice.chat("<green>► <white>Anvil opened!");
    Notice targetAnvilOpened = Notice.chat("<green>► <white>Opened an anvil for {PLAYER}!");

    Notice cartographyOpened = Notice.chat("<green>► <white>Cartography table opened!");
    Notice targetCartographyOpened = Notice.chat("<green>► <white>Opened a cartography table for {PLAYER}!");

    Notice enderchestOpened = Notice.chat("<green>► <white>Ender chest opened!");
    Notice targetEnderchestOpened = Notice.chat("<green>► <white>Opened an ender chest for {PLAYER}!");

    Notice grindstoneOpened = Notice.chat("<green>► <white>Grindstone opened!");
    Notice targetGrindstoneOpened = Notice.chat("<green>► <white>Opened a grindstone for {PLAYER}!");

    Notice loomOpened = Notice.chat("<green>► <white>Loom opened!");
    Notice targetLoomOpened = Notice.chat("<green>► <white>Opened a loom for {PLAYER}!");

    Notice smithingOpened = Notice.chat("<green>► <white>Smithing table opened!");
    Notice targetSmithingOpened = Notice.chat("<green>► <white>Opened a smithing table for {PLAYER}!");

    Notice stonecutterOpened = Notice.chat("<green>► <white>Stonecutter opened!");
    Notice targetStonecutterOpened = Notice.chat("<green>► <white>Opened a stonecutter for {PLAYER}!");

    Notice workbenchOpened = Notice.chat("<green>► <white>Workbench opened!");
    Notice targetWorkbenchOpened = Notice.chat("<green>► <white>Opened a workbench for {PLAYER}!");
}
