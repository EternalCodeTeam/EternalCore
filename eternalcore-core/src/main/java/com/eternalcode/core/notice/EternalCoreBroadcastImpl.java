package com.eternalcode.core.notice;

import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.user.User;
import com.eternalcode.multification.adventure.AudienceConverter;
import com.eternalcode.multification.executor.AsyncExecutor;
import com.eternalcode.multification.locate.LocaleProvider;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.notice.NoticeBroadcastImpl;
import com.eternalcode.multification.notice.provider.TextMessageProvider;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.notice.resolver.text.TextContent;
import com.eternalcode.multification.platform.PlatformBroadcaster;
import com.eternalcode.multification.shared.Replacer;
import com.eternalcode.multification.translation.TranslationProvider;
import com.eternalcode.multification.viewer.ViewerProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        Replacer<Viewer> replacer,
        NoticeResolverRegistry noticeRegistry
    ) {
        super(
            asyncExecutor,
            translationProvider,
            viewerProvider,
            platformBroadcaster,
            localeProvider,
            audienceConverter,
            replacer,
            noticeRegistry
        );
    }

    public <CONTEXT> B placeholders(Placeholders<CONTEXT> placeholders, CONTEXT context) {
        return this.formatter(placeholders.toFormatter(context));
    }

    public B sender(CommandSender sender) {
        if (sender instanceof Player player) {
            return this.player(player.getUniqueId());
        }

        return this.console();
    }

    public B user(User user) {
        return this.player(user.getUniqueId());
    }

    public B notice(NoticeTextType type, TextMessageProvider<Translation> extractor) {
        this.notifications.add(translation -> {
            List<String> list = Collections.singletonList(extractor.extract(translation));

            TextContent content = noticeRegistry.createTextNotice(type.getNoticeKey(), new ArrayList<>(list));

            return Notice.of(type.getNoticeKey(), content);
        });

        return this.getThis();
    }

    public B notice(NoticeTextType type, String message) {
        this.notifications.add(translation -> {
            List<String> list = Collections.singletonList(message);

            TextContent content = noticeRegistry.createTextNotice(type.getNoticeKey(), new ArrayList<>(list));

            return Notice.of(type.getNoticeKey(), content);
        });

        return this.getThis();
    }

    public B messages(Function<Translation, List<String>> messages) {
        this.notifications.add(translation -> {
            List<String> list = messages.apply(translation);
            return Notice.chat(list);
        });

        return this.getThis();
    }
}
