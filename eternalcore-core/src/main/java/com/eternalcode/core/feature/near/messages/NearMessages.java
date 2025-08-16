package com.eternalcode.core.feature.near.messages;

import com.eternalcode.multification.notice.Notice;

public interface NearMessages {

    Notice entitiesNotFound();
    Notice entitiesFound();
    Notice entityEntry();
    Notice invalidEntityType();

}
