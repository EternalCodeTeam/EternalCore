package com.eternalcode.core.feature.container.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENContainerMessages extends OkaeriConfig implements ContainerMessages {

    Notice anvilOpened = Notice.chat("<color:#9d6eef>► <white>Anvil opened!");
    Notice targetAnvilOpened = Notice.chat("<color:#9d6eef>► <white>Opened an anvil for {PLAYER}!");

    Notice cartographyOpened = Notice.chat("<color:#9d6eef>► <white>Cartography table opened!");
    Notice targetCartographyOpened = Notice.chat("<color:#9d6eef>► <white>Opened a cartography table for {PLAYER}!");

    Notice enderchestOpened = Notice.chat("<color:#9d6eef>► <white>Ender chest opened!");
    Notice targetEnderchestOpened = Notice.chat("<color:#9d6eef>► <white>Opened an ender chest for {PLAYER}!");

    Notice grindstoneOpened = Notice.chat("<color:#9d6eef>► <white>Grindstone opened!");
    Notice targetGrindstoneOpened = Notice.chat("<color:#9d6eef>► <white>Opened a grindstone for {PLAYER}!");

    Notice loomOpened = Notice.chat("<color:#9d6eef>► <white>Loom opened!");
    Notice targetLoomOpened = Notice.chat("<color:#9d6eef>► <white>Opened a loom for {PLAYER}!");

    Notice smithingOpened = Notice.chat("<color:#9d6eef>► <white>Smithing table opened!");
    Notice targetSmithingOpened = Notice.chat("<color:#9d6eef>► <white>Opened a smithing table for {PLAYER}!");

    Notice stonecutterOpened = Notice.chat("<color:#9d6eef>► <white>Stonecutter opened!");
    Notice targetStonecutterOpened = Notice.chat("<color:#9d6eef>► <white>Opened a stonecutter for {PLAYER}!");

    Notice workbenchOpened = Notice.chat("<color:#9d6eef>► <white>Workbench opened!");
    Notice targetWorkbenchOpened = Notice.chat("<color:#9d6eef>► <white>Opened a workbench for {PLAYER}!");
}
