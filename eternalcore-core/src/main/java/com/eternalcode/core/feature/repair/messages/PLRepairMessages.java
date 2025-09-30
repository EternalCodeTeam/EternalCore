package com.eternalcode.core.feature.repair.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLRepairMessages extends OkaeriConfig implements RepairMessages {

    public Notice itemRepaired = Notice.chat ("<green>► <white>Item w twojej ręce został naprawiony!");
    public Notice allItemsRepaired = Notice.chat ("<green>► <white>Wszystkie itemy w twoim ekwipunku zostały naprawione!");
    public Notice armorRepaired = Notice.chat("<green>► <white>Twoja zbroja została naprawiona!");

    public Notice delay = Notice.chat ("<red>✘ <dark_red>Możesz użyć tej komendy za <dark_red>{TIME}!");

    public Notice cannotRepair = Notice.chat ("<red>✘ <dark_red>Nie możesz naprawić tego itemu!");

    public Notice needDamagedItem = Notice.chat ("<red>✘ <dark_red>Nie masz żadnych uszkodzonych przedmiotów do naprawy!");
}
