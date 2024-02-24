package com.eternalcode.core.notice;

import com.eternalcode.multification.notice.Notice;
import panda.std.Option;

@FunctionalInterface
public interface OptionNoticeProvider<Translation> {

    Option<Notice> extract(Translation translation);

}