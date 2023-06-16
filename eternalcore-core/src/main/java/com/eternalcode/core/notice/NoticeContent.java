package com.eternalcode.core.notice;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.List;

sealed interface NoticeContent {

    record Text(List<String> messages) implements NoticeContent { }

    record Times(Duration fadeIn, Duration stay, Duration fadeOut) implements NoticeContent { }

    record Music(Sound sound, @Nullable SoundCategory category, float pitch, float volume) implements NoticeContent { }

    record None() implements NoticeContent {

        public static final None INSTANCE = new None();

    }

}
