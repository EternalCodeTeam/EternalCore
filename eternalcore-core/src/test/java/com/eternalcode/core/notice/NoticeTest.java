package com.eternalcode.core.notice;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class NoticeTest {

    static final String TEXT = "Hello World";
    static final String TEXT_SECOND = "Hello World 2";

    @Test
    @DisplayName("Should create a notice with a single message")
    void testSingleChat() {
        Notice notice = Notice.chat(TEXT);

        assertParts(1, notice);
        assertMessage(notice, 0, NoticeType.CHAT, TEXT);
    }

    @Test
    @DisplayName("Should create a notice with multiple messages")
    void testMultipleChat() {
        Notice notice = Notice.chat(
            TEXT,
            TEXT_SECOND
        );

        assertParts(1, notice);
        assertMessage(notice, 0, NoticeType.CHAT, TEXT, TEXT_SECOND);
    }

    @Test
    @DisplayName("Should create a notice with action bar")
    void testSingleActionBar() {
        Notice notice = Notice.actionbar(TEXT);

        assertParts(1, notice);
        assertMessage(notice, 0, NoticeType.ACTION_BAR, TEXT);
    }

    @Test
    @DisplayName("Should create a notice with single title")
    void testSingleTitle() {
        Notice notice = Notice.title(TEXT);

        assertParts(1, notice);
        assertMessage(notice, 0, NoticeType.TITLE, TEXT);
    }

    @Test
    @DisplayName("Should create a notice with title and subtitle")
    void testTitleSubtitle() {
        Notice notice = Notice.title(TEXT, TEXT_SECOND);

        assertParts(2, notice);

        assertMessage(notice, 0, NoticeType.TITLE, TEXT);
        assertMessage(notice, 1, NoticeType.SUBTITLE, TEXT_SECOND);
    }

    @Test
    @DisplayName("Should create a notice with title, subtitle and times")
    void testTitleSubtitleTimes() {
        Notice notice = Notice.builder()
            .title(TEXT)
            .subtitle(TEXT_SECOND)
            .times(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(3))
            .build();

        assertParts(3, notice);

        assertMessage(notice, 0, NoticeType.TITLE, TEXT);
        assertMessage(notice, 1, NoticeType.SUBTITLE, TEXT_SECOND);
        assertTimes(notice, 2, Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(3));
    }

    @Test
    @DisplayName("Should create a notice with sound")
    void testSound() {
        Notice notice = Notice.builder()
            .sound(Sound.AMBIENT_CAVE, 1.0f, 1.0f)
            .build();

        assertParts(1, notice);
        assertSound(notice, 0, Sound.AMBIENT_CAVE, 1.0f, 1.0f);
    }

    @Test
    @DisplayName("Should create a notice with multiple messages and multiple parts")
    void testMultipleParts() {
        Notice notice = Notice.builder()
            .chat(TEXT)
            .actionBar(TEXT)
            .title(TEXT)
            .subtitle(TEXT_SECOND)
            .times(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(3))
            .sound(Sound.AMBIENT_CAVE, 1.0f, 2.0f)
            .build();

        assertParts(6, notice);

        assertMessage(notice, 0, NoticeType.CHAT, TEXT);
        assertMessage(notice, 1, NoticeType.ACTION_BAR, TEXT);
        assertMessage(notice, 2, NoticeType.TITLE, TEXT);
        assertMessage(notice, 3, NoticeType.SUBTITLE, TEXT_SECOND);
        assertTimes(notice, 4, Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(3));
        assertSound(notice, 5, Sound.AMBIENT_CAVE, 1.0f, 2.0f);
    }

    private void assertParts(int expected, Notice notice) {
        assertEquals(expected, notice.parts().size());
    }

    private void assertMessage(Notice notice, int index, NoticeType type, String... expected) {
        Notice.Part<?> part = notice.parts().get(index);
        NoticeContent.Text textInput = assertInstanceOf(NoticeContent.Text.class, part.content());

        assertEquals(type, part.type());
        assertEquals(expected.length, textInput.messages().size());

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], textInput.messages().get(i));
        }
    }

    private void assertTimes(Notice notice, int index, Duration in, Duration stay, Duration out) {
        Notice.Part<?> part = notice.parts().get(index);
        NoticeContent.Times delay = assertInstanceOf(NoticeContent.Times.class, part.content());

        assertEquals(NoticeType.TITLE_TIMES, part.type());
        assertEquals(in, delay.fadeIn());
        assertEquals(stay, delay.stay());
        assertEquals(out, delay.fadeOut());
    }

    private void assertSound(Notice notice, int index, Sound sound, float volume, float pitch) {
        assertSound(notice, index, sound, volume, pitch, null);
    }

    private void assertSound(Notice notice, int index, Sound sound, float volume, float pitch, SoundCategory category) {
        Notice.Part<?> part = notice.parts().get(index);
        NoticeContent.Music soundInput = assertInstanceOf(NoticeContent.Music.class, part.content());

        assertEquals(NoticeType.SOUND, part.type());
        assertEquals(sound, soundInput.sound());
        assertEquals(volume, soundInput.volume());
        assertEquals(pitch, soundInput.pitch());
        assertEquals(category, soundInput.category());
    }

}
