package com.eternalcode.core.feature.home.messages;

import com.eternalcode.multification.notice.Notice;

public interface HomeMessages {
    Notice homeList();
    Notice create();
    Notice delete();
    Notice limit();
    Notice overrideHomeLocation();
    Notice noHomesOwned();
    String noHomesOwnedPlaceholder();

    Notice overrideHomeLocationAsAdmin();
    Notice playerNoOwnedHomes();
    Notice createAsAdmin();
    Notice deleteAsAdmin();
    Notice homeListAsAdmin();
}
