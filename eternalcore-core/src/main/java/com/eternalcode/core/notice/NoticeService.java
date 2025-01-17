package com.eternalcode.core.notice;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.feature.language.LanguageService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.multification.Multification;
import com.eternalcode.multification.adventure.AudienceConverter;
import com.eternalcode.multification.executor.AsyncExecutor;
import com.eternalcode.multification.locate.LocaleProvider;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.platform.PlatformBroadcaster;
import com.eternalcode.multification.shared.Replacer;
import com.eternalcode.multification.translation.TranslationProvider;
import com.eternalcode.multification.viewer.ViewerProvider;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

@Service
public class NoticeService extends Multification<Viewer, Translation> {

    private final UserManager userManager;
    private final Scheduler scheduler;
    private final Server server;

    private final AudienceProvider audienceProvider;
    private final TranslationManager translationManager;
    private final PlaceholderRegistry registry;
    private final MiniMessage miniMessage;

    private final NoticeResolverRegistry noticeRegistry;
    private final LanguageService languageService;

    @Inject
    public NoticeService(
        UserManager userManager,
        Scheduler scheduler,
        Server server,
        AudienceProvider audienceProvider,
        TranslationManager translationManager,
        PlaceholderRegistry registry,
        MiniMessage miniMessage,
        NoticeResolverRegistry noticeRegistry,
        LanguageService languageService
    ) {
        this.userManager = userManager;
        this.scheduler = scheduler;
        this.server = server;
        this.audienceProvider = audienceProvider;
        this.translationManager = translationManager;
        this.registry = registry;
        this.miniMessage = miniMessage;
        this.noticeRegistry = noticeRegistry;
        this.languageService = languageService;
    }

    @Override
    public @NotNull ViewerProvider<Viewer> viewerProvider() {
        return new BukkitViewerProvider(this.userManager, this.server);
    }

    @Override
    public @NotNull TranslationProvider<Translation> translationProvider() {
        return this.translationManager;
    }

    @Override
    public @NotNull AudienceConverter<Viewer> audienceConverter() {
        return viewer -> {
            if (viewer.isConsole()) {
                return this.audienceProvider.console();
            }

            return this.audienceProvider.player(viewer.getUniqueId());
        };
    }

    @Override
    public @NotNull LocaleProvider<Viewer> localeProvider() {
        return viewer -> {
            if (viewer.isConsole()) {
                return Language.DEFAULT.toLocale();
            }

            return languageService.getLanguageNow(viewer.getUniqueId()).toLocale();
        };
    }

    @Override
    public @NotNull AsyncExecutor asyncExecutor() {
        return this.scheduler::runAsync;
    }

    @Override
    public @NotNull Replacer<Viewer> globalReplacer() {
        return (viewer, text) -> this.registry.format(text, viewer);
    }

    @Override
    public NoticeResolverRegistry getNoticeRegistry() {
        return this.noticeRegistry;
    }

    @Override
    public PlatformBroadcaster platformBroadcaster() {
        return PlatformBroadcaster.create(this.serializer(), this.noticeRegistry);
    }

    @Override
    protected @NotNull ComponentSerializer<Component, Component, String> serializer() {
        return this.miniMessage;
    }

    @Override
    public EternalCoreBroadcast<Viewer, Translation, ?> create() {
        return new EternalCoreBroadcast<>(
            this.asyncExecutor(),
            this.translationProvider(),
            this.viewerProvider(),
            this.platformBroadcaster(),
            this.localeProvider(),
            this.audienceConverter(),
            this.globalReplacer(),
            this.noticeRegistry
        );
    }
}
