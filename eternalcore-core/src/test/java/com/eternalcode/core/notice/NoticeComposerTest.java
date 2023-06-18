package com.eternalcode.core.notice;

import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.reflect.Visibility;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("FieldMayBeFinal")
class NoticeComposerTest {

    private static final Cdn cdn = CdnFactory.createYamlLike()
        .getSettings()
        .withComposer(Notice.class, new NoticeComposer())
        .withMemberResolver(Visibility.PACKAGE_PRIVATE)
        .build();


    static class ConfigEmpty {
        Notice notice = Notice.empty();
    }

    @Test
    @DisplayName("Should serialize and deserialize empty notice to empty entry")
    void serializeEmptyNoticeToEmptyEntry() {
        ConfigEmpty configEmpty = assertRender(new ConfigEmpty(),
            """
                notice: []
                """
        );

        assertEquals(0, configEmpty.notice.parts().size());
    }

    static class ConfigOneLineChat {
        Notice notice = Notice.chat("Hello world");
    }

    @Test
    @DisplayName("Should serialize and deserialize simple chat notice to one line entry")
    void serializeSimpleChatNoticeToOneLineEntry() {
        ConfigOneLineChat oneLineChat = assertRender(new ConfigOneLineChat(),
            """
            notice: "Hello world"
            """
        );

        assertEquals(1, oneLineChat.notice.parts().size());

        Notice.Part<?> part = oneLineChat.notice.parts().get(0);
        NoticeContent.Text text = assertInstanceOf(NoticeContent.Text.class, part.content());
        assertEquals(NoticeType.CHAT, part.type());
        assertEquals("Hello world", text.messages().get(0));
    }


    static class ConfigMultiLineChat {
        Notice notice = Notice.chat("First line", "Second line");
    }
    @Test
    @DisplayName("Should serialize simple chat notice to multiline entry")
    void serializeSimpleChatNoticeToMultilineEntry() {
        ConfigMultiLineChat configMultiLineChat = assertRender(new ConfigMultiLineChat(),
            """
                notice:
                  - "First line"
                  - "Second line"
                """);

        assertEquals(1, configMultiLineChat.notice.parts().size());

        Notice.Part<?> part = configMultiLineChat.notice.parts().get(0);
        NoticeContent.Text text = assertInstanceOf(NoticeContent.Text.class, part.content());
        assertEquals(NoticeType.CHAT, part.type());
        assertEquals("First line", text.messages().get(0));
        assertEquals("Second line", text.messages().get(1));
    }

    static class ConfigSimpleTitle {
        Notice notice = Notice.title("Hello world");
    }
    @Test
    @DisplayName("Should serialize simple title notice to title section")
    void serializeSimpleTitleNoticeToOneLineEntry() {
        ConfigSimpleTitle configSimpleTitle = assertRender(new ConfigSimpleTitle(),
            """
                notice:
                  title: "Hello world"
                """);

        assertEquals(1, configSimpleTitle.notice.parts().size());

        Notice.Part<?> part = configSimpleTitle.notice.parts().get(0);
        NoticeContent.Text title = assertInstanceOf(NoticeContent.Text.class, part.content());
        assertEquals(NoticeType.TITLE, part.type());
        assertEquals("Hello world", title.messages().get(0));
    }

    static  class ConfigFullTitle {
        Notice notice = Notice.title("Title", "Subtitle", Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1));
    }
    @Test
    @DisplayName("Should serialize title subtitle with delay notice to title section")
    void serializeTitleSubtitleWithDelayNoticeToOneLineEntry() {
        ConfigFullTitle configFullTitle = assertRender(new ConfigFullTitle(),
            """
                notice:
                  title: "Title"
                  subtitle: "Subtitle"
                  times: "1s 2s 1s"
                """);

        assertEquals(3, configFullTitle.notice.parts().size());

        Notice.Part<?> titlePart = configFullTitle.notice.parts().get(0);
        NoticeContent.Text title = assertInstanceOf(NoticeContent.Text.class, titlePart.content());
        assertEquals(NoticeType.TITLE, titlePart.type());
        assertEquals("Title", title.messages().get(0));

        Notice.Part<?> subtitlePart = configFullTitle.notice.parts().get(1);
        NoticeContent.Text subtitle = assertInstanceOf(NoticeContent.Text.class, subtitlePart.content());
        assertEquals(NoticeType.SUBTITLE, subtitlePart.type());
        assertEquals("Subtitle", subtitle.messages().get(0));

        Notice.Part<?> timesPart = configFullTitle.notice.parts().get(2);
        NoticeContent.Times times = assertInstanceOf(NoticeContent.Times.class, timesPart.content());
        assertEquals(NoticeType.TITLE_TIMES, timesPart.type());
        assertEquals(1, times.fadeIn().getSeconds());
        assertEquals(2, times.stay().getSeconds());
        assertEquals(1, times.fadeOut().getSeconds());
    }

    static class ConfigSimpleActionBar {
        Notice notice = Notice.actionbar("Hello world");
    }
    @Test
    @DisplayName("Should serialize simple actionbar notice to actionbar section")
    void serializeSimpleActionBarNoticeToOneLineEntry() {
        ConfigSimpleActionBar configSimpleActionBar = assertRender(new ConfigSimpleActionBar(),
            """
                notice:
                  actionbar: "Hello world"
                """);

        assertEquals(1, configSimpleActionBar.notice.parts().size());

        Notice.Part<?> part = configSimpleActionBar.notice.parts().get(0);
        NoticeContent.Text text = assertInstanceOf(NoticeContent.Text.class, part.content());
        assertEquals(NoticeType.ACTION_BAR, part.type());
        assertEquals("Hello world", text.messages().get(0));
    }

    static class ConfigHideTitle {
        Notice notice = Notice.hideTitle();
    }
    @Test
    @DisplayName("Should serialize hide title notice with hide title property")
    void serializeHideTitleNoticeWithHideTitleProperty() {
        ConfigHideTitle configHideTitle = assertRender(new ConfigHideTitle(),
            """
                notice:
                  titleHide: true
                """);

        assertEquals(1, configHideTitle.notice.parts().size());

        Notice.Part<?> part = configHideTitle.notice.parts().get(0);
        assertInstanceOf(NoticeContent.None.class, part.content());
        assertEquals(NoticeType.TITLE_HIDE, part.type());
    }

    static class ConfigSound {
        Notice notice = Notice.sound(Sound.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 1.0f, 1.0f);
    }
    @Test
    @DisplayName("Should serialize sound notice with sound property")
    void serializeSoundNoticeWithSoundProperty() {
        ConfigSound configSound = assertRender(new ConfigSound(),
            """
                notice:
                  sound: "BLOCK_ANVIL_LAND MASTER 1.0 1.0"
                """);

        assertEquals(1, configSound.notice.parts().size());

        Notice.Part<?> part = configSound.notice.parts().get(0);
        NoticeContent.Music sound = assertInstanceOf(NoticeContent.Music.class, part.content());
        assertEquals(NoticeType.SOUND, part.type());
        assertEquals(Sound.BLOCK_ANVIL_LAND, sound.sound());
        assertEquals(SoundCategory.MASTER, sound.category());
        assertEquals(1.0f, sound.volume());
        assertEquals(1.0f, sound.pitch());
    }

    static class ConfigSoundWithoutCategory {
        Notice notice = Notice.sound(Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
    }
    @Test
    @DisplayName("Should serialize sound notice without category property")
    void serializeSoundNoticeWithoutCategoryProperty() {
        ConfigSoundWithoutCategory configSoundWithoutCategory = assertRender(new ConfigSoundWithoutCategory(),
            """
                notice:
                  sound: "BLOCK_ANVIL_LAND 1.0 1.0"
                """);

        assertEquals(1, configSoundWithoutCategory.notice.parts().size());

        Notice.Part<?> part = configSoundWithoutCategory.notice.parts().get(0);
        NoticeContent.Music sound = assertInstanceOf(NoticeContent.Music.class, part.content());
        assertEquals(NoticeType.SOUND, part.type());
        assertEquals(Sound.BLOCK_ANVIL_LAND, sound.sound());
        assertNull(sound.category());
        assertEquals(1.0f, sound.volume());
        assertEquals(1.0f, sound.pitch());
    }

    @SuppressWarnings("unchecked")
    private <T> T assertRender(T entity, String expected) {
        String actual = cdn.render(entity).orThrow(exception -> new RuntimeException(exception));

        actual = removeBlankNewLines(actual);
        expected = removeBlankNewLines(expected);

        assertEquals(expected, actual);

        return (T) cdn.load(Source.of(expected), entity.getClass()).orThrow(exception -> new RuntimeException(exception));
    }

    private String removeBlankNewLines(String string) {
        return string
            .replaceAll("\n+", "\n")
            .replaceAll("\n+$", "")
            .replaceAll("^\n+", "");
    }

}
