package com.eternalcode.core.feature.vanish.messages;

import com.eternalcode.multification.notice.Notice;

public interface VanishMessages {

    Notice vanishEnabled();
    Notice vanishDisabled();

    Notice vanishEnabledOther();
    Notice vanishDisabledOther();

    Notice joinedInVanishMode();
    Notice playerJoinedInVanishMode();

    Notice cantBlockPlaceWhileVanished();
    Notice cantBlockBreakWhileVanished();
    Notice cantUseChatWhileVanished();
    Notice cantDropItemsWhileVanished();
    Notice cantPickupItemsWhileVanished();
    Notice cantOpenInventoryWhileVanished();


}
