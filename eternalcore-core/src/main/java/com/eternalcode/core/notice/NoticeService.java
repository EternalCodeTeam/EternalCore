package com.eternalcode.core.notice;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.multification.Multification;
import com.eternalcode.multification.adventure.AudienceConverter;
import com.eternalcode.multification.executor.AsyncExecutor;
import com.eternalcode.multification.locate.LocaleProvider;
import com.eternalcode.multification.platform.PlatformBroadcaster;
import com.eternalcode.multification.shared.Replacer;
import com.eternalcode.multification.translation.TranslationProvider;
import com.eternalcode.multification.viewer.ViewerProvider;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

@Service
public class NoticeService extends Multification<Viewer, Translation> {

    private final UserManager userManager;
    private final TranslationManager translationManager;
    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;
    private final PlaceholderRegistry registry;
    private final Server server;
    private final Scheduler scheduler;

    @Inject
    public NoticeService(
        UserManager userManager,
        TranslationManager translationManager,
        AudienceProvider audienceProvider,
        MiniMessage miniMessage,
        PlaceholderRegistry registry,
        Server server,
        Scheduler scheduler
    ) {
        this.userManager = userManager;
        this.translationManager = translationManager;
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
        this.registry = registry;
        this.server = server;
        this.scheduler = scheduler;
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
        return viewer -> this.audienceProvider.player(viewer.getUniqueId());
    }

    @Override
    protected @NotNull PlatformBroadcaster platformBroadcaster() {
        return PlatformBroadcaster.withSerializer(this.miniMessage);
    }

    @Override
    protected @NotNull Replacer<Viewer> globalReplacer() {
        return (viewer, text) -> this.registry.format(text, viewer);
    }

    @Override
    protected @NotNull AsyncExecutor asyncExecutor() {
        return this.scheduler::async;
    }

    @Override
    protected @NotNull LocaleProvider<Viewer> localeProvider() {
        return viewer -> viewer.getLanguage().toLocale();
    }

    @Override
    public EternalCoreBroadcastImpl<Viewer, Translation, ?> create() {
        return new EternalCoreBroadcastImpl<>(
            this.asyncExecutor(),
            this.translationProvider(),
            this.viewerProvider(),
            this.platformBroadcaster(),
            this.localeProvider(),
            this.audienceConverter(),
            this.globalReplacer()
        );
    }
}
