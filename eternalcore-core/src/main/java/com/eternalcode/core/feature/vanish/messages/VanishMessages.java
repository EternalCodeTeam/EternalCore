package com.eternalcode.core.feature.vanish.messages;

import com.eternalcode.multification.notice.Notice;

public interface VanishMessages {

    Notice vanishEnabled();
    Notice vanishDisabled();

    Notice vanishEnabledForOther();
    Notice vanishDisabledForOther();

    Notice vanishEnabledByStaff();
    Notice vanishDisabledByStaff();

    Notice currentlyInVanish();

    Notice joinedInVanish();
    Notice playerJoinedInVanish();

    Notice cantBlockPlaceWhileVanished();
    Notice cantBlockBreakWhileVanished();
    Notice cantUseChatWhileVanished();
    Notice cantDropItemsWhileVanished();
    Notice cantOpenInventoryWhileVanished();


}
