package com.eternalcode.core.feature.near.messages;

import com.eternalcode.multification.notice.Notice;

public interface NearMessages {

    Notice noEntitiesFound();
    Notice entitiesShown();

    Notice entityListHeader();
    Notice entityListEntry();

}
