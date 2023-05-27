package com.eternalcode.core.notice.extractor;

import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;

@FunctionalInterface
public interface NoticeExtractor {

    Notice extract(Translation translation);

}
