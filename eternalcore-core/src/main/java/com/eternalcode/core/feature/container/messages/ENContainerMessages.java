package com.eternalcode.core.feature.container.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENContainerMessages extends OkaeriConfig implements ContainerMessages {

    public Notice anvilOpened = Notice.chat("<green>► <white>Anvil opened!");
    public Notice targetAnvilOpened = Notice.chat("<green>► <white>Opened an anvil for {PLAYER}!");

    public Notice cartographyOpened = Notice.chat("<green>► <white>Cartography table opened!");
    public Notice targetCartographyOpened = Notice.chat("<green>► <white>Opened a cartography table for {PLAYER}!");

    public Notice disposalOpened = Notice.chat("<green>► <white>Disposal opened!");
    public Notice targetDisposalOpened = Notice.chat("<green>► <white>Opened a disposal for {PLAYER}!");

    public Notice enderchestOpened = Notice.chat("<green>► <white>Ender chest opened!");
    public Notice targetEnderchestOpened = Notice.chat("<green>► <white>Opened an ender chest for {PLAYER}!");

    public Notice grindstoneOpened = Notice.chat("<green>► <white>Grindstone opened!");
    public Notice targetGrindstoneOpened = Notice.chat("<green>► <white>Opened a grindstone for {PLAYER}!");

    public Notice loomOpened = Notice.chat("<green>► <white>Loom opened!");
    public Notice targetLoomOpened = Notice.chat("<green>► <white>Opened a loom for {PLAYER}!");

    public Notice smithingOpened = Notice.chat("<green>► <white>Smithing table opened!");
    public Notice targetSmithingOpened = Notice.chat("<green>► <white>Opened a smithing table for {PLAYER}!");

    public Notice stonecutterOpened = Notice.chat("<green>► <white>Stonecutter opened!");
    public Notice targetStonecutterOpened = Notice.chat("<green>► <white>Opened a stonecutter for {PLAYER}!");

    public Notice workbenchOpened = Notice.chat("<green>► <white>Workbench opened!");
    public Notice targetWorkbenchOpened = Notice.chat("<green>► <white>Opened a workbench for {PLAYER}!");
}
