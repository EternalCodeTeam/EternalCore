package com.eternalcode.core.feature.repair.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLRepairMessages extends OkaeriConfig implements RepairMessages {

    Notice itemRepaired = Notice.chat("<color:#9d6eef>► <white>Item w twojej ręce został naprawiony!");
    Notice allItemsRepaired = Notice.chat("<color:#9d6eef>► <white>Wszystkie itemy w twoim ekwipunku zostały naprawione!");
    Notice armorRepaired = Notice.chat("<color:#9d6eef>► <white>Twoja zbroja została naprawiona!");

    Notice delay = Notice.chat("<red>✘ <dark_red>Możesz użyć tej komendy za <dark_red>{TIME}!");

    Notice cannotRepair = Notice.chat("<red>✘ <dark_red>Nie możesz naprawić tego itemu!");

    Notice needDamagedItem = Notice.chat("<red>✘ <dark_red>Nie masz żadnych uszkodzonych przedmiotów do naprawy!");
}
