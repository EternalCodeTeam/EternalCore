package com.eternalcode.core.feature.repair.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENRepairMessages extends OkaeriConfig implements RepairMessages {

    public Notice itemRepaired = Notice.chat("<green>► <white>Item in your hand has been repaired!");
    public Notice allItemsRepaired = Notice.chat("<green>► <white>All items in your inventory has been repaired!");
    public Notice armorRepaired = Notice.chat("<green>► <white>Your armor has been repaired!");

    public Notice delay = Notice.chat("<red>✘ <dark_red>You can use this command in <dark_red>{TIME}!");

}
