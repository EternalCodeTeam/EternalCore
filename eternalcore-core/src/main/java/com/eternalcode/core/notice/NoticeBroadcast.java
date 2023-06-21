package com.eternalcode.core.notice;

import com.eternalcode.core.notice.extractor.NoticeExtractor;
import com.eternalcode.core.notice.extractor.OptionalNoticeExtractor;
import com.eternalcode.core.notice.extractor.TranslatedMessageExtractor;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.user.User;
import com.eternalcode.core.viewer.Viewer;
import panda.std.Option;
import panda.utilities.text.Formatter;

import javax.annotation.CheckReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public interface NoticeBroadcast {

    @CheckReturnValue
    NoticeBroadcast user(User user);

    @CheckReturnValue
    NoticeBroadcast player(UUID player);

    @CheckReturnValue
    NoticeBroadcast players(Iterable<UUID> players);

    @CheckReturnValue
    NoticeBroadcast viewer(Viewer viewer);

    @CheckReturnValue
    NoticeBroadcast console();

    @CheckReturnValue
    NoticeBroadcast all();

    @CheckReturnValue
    NoticeBroadcast onlinePlayers();

    @CheckReturnValue
    NoticeBroadcast message(TranslatedMessageExtractor extractor);

    @CheckReturnValue
    NoticeBroadcast messages(Function<Translation, List<String>> function);

    @CheckReturnValue
    NoticeBroadcast staticNotice(Notice notification);

    @CheckReturnValue
    NoticeBroadcast noticeOption(OptionalNoticeExtractor extractor);

    @CheckReturnValue
    NoticeBroadcast notice(NoticeExtractor extractor);

    @CheckReturnValue
    NoticeBroadcast notice(NoticeTextType type, String... text);

    @CheckReturnValue
    NoticeBroadcast notice(NoticeTextType type, Collection<String> text);

    @CheckReturnValue
    NoticeBroadcast notice(NoticeTextType type, TranslatedMessageExtractor extractor);

    @CheckReturnValue
    NoticeBroadcast placeholder(String from, String to);

    @CheckReturnValue
    NoticeBroadcast placeholder(String from, Option<String> to);

    @CheckReturnValue
    NoticeBroadcast placeholder(String from, Optional<String> to);

    @CheckReturnValue
    NoticeBroadcast placeholder(String from, Supplier<String> to);

    @CheckReturnValue
    NoticeBroadcast placeholder(String from, TranslatedMessageExtractor extractor);

    @CheckReturnValue
    <T> NoticeBroadcast placeholder(Placeholders<T> placeholder, T context);

    @CheckReturnValue
    NoticeBroadcast formatter(Formatter... formatters);

    void sendAsync();

    void send();

}
