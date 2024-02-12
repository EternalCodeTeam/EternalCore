package com.eternalcode.core.notice;

import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.user.User;
import com.eternalcode.multification.adventure.AudienceConverter;
import com.eternalcode.multification.executor.AsyncExecutor;
import com.eternalcode.multification.locate.LocaleProvider;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.notice.NoticeBroadcastImpl;
import com.eternalcode.multification.notice.NoticeContent;
import com.eternalcode.multification.notice.NoticeType;
import com.eternalcode.multification.notice.provider.TextMessageProvider;
import com.eternalcode.multification.platform.PlatformBroadcaster;
import com.eternalcode.multification.shared.Replacer;
import com.eternalcode.multification.translation.TranslationProvider;
import com.eternalcode.multification.viewer.ViewerProvider;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.annotations.CheckReturnValue;
import panda.std.Option;

/**
 * This class is an extension of {@link NoticeBroadcastImpl} that provides more methods for creating notices.
 */
public class EternalCoreBroadcastImpl<Viewer, Translation, B extends EternalCoreBroadcastImpl<Viewer, Translation, B>>
    extends NoticeBroadcastImpl<Viewer, Translation, B> {

    public EternalCoreBroadcastImpl(
        AsyncExecutor asyncExecutor,
        TranslationProvider<Translation> translationProvider,
        ViewerProvider<Viewer> viewerProvider,
        PlatformBroadcaster platformBroadcaster,
        LocaleProvider<Viewer> localeProvider,
        AudienceConverter<Viewer> audienceConverter,
        Replacer<Viewer> replacer
    ) {
        super(
            asyncExecutor,
            translationProvider,
            viewerProvider,
            platformBroadcaster,
            localeProvider,
            audienceConverter,
            replacer
        );
    }

    public <CONTEXT> B placeholders(Placeholders<CONTEXT> placeholders, CONTEXT context) {
        return this.formatter(placeholders.toFormatter(context));
    }

    public B notice(NoticeTextType type, TextMessageProvider<Translation> extractor) {
        this.notifications.add(translation -> {
            List<String> list = Collections.singletonList(extractor.extract(translation));
            NoticeContent.Text content = new NoticeContent.Text(list);

            NoticeType noticeType = type.getType();
            return Notice.of(noticeType, content);
        });

        return this.getThis();
    }

    public B notice(NoticeTextType type, String message) {
        this.notifications.add(translation -> {
            List<String> list = Collections.singletonList(message);
            NoticeContent.Text content = new NoticeContent.Text(list);

            NoticeType noticeType = type.getType();
            return Notice.of(noticeType, content);
        });

        return this.getThis();
    }

    public B messages(Function<Translation, List<String>> messages) {
        this.notifications.add(translation -> {
            List<String> list = messages.apply(translation);
            NoticeContent.Text content = new NoticeContent.Text(list);

            return Notice.of(NoticeType.CHAT, content);
        });

        return this.getThis();
    }

    public B user(User user) {
        return this.player(user.getUniqueId());
    }
}
