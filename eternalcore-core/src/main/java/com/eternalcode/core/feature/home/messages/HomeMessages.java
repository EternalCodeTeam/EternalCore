package com.eternalcode.core.feature.home.messages;

import com.eternalcode.multification.notice.Notice;

public interface HomeMessages {
    Notice homeList();
    String homeListEntryFormat();
    Notice create();
    Notice delete();
    Notice limit();
    Notice overrideHomeLocation();
    Notice renamed();
    Notice renameNameTaken();
    Notice noHomesOwned();
    String noHomesOwnedPlaceholder();
    Notice signOpenFailed();

    Notice overrideHomeLocationAsAdmin();
    Notice playerNoOwnedHomes();
    Notice createAsAdmin();
    Notice deleteAsAdmin();
    Notice homeListAsAdmin();
    String homeListEntryFormatAsAdmin();
    Notice noHomesOnListAsAdmin();
    Notice teleportedAsAdmin();
}
