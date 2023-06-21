package com.eternalcode.core.notice.extractor;

import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import panda.std.Option;

@FunctionalInterface
public interface OptionalNoticeExtractor {

    Option<Notice> extract(Translation translation);

}
