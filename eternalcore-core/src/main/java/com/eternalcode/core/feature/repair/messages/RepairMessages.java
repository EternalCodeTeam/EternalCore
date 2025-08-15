package com.eternalcode.core.feature.repair.messages;

import com.eternalcode.multification.notice.Notice;

public interface RepairMessages {

    Notice itemRepaired();
    Notice allItemsRepaired();
    Notice armorRepaired();
    Notice delay();
}
