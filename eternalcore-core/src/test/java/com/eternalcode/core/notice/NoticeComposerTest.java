package com.eternalcode.core.notice;

import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.reflect.Visibility;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("Should serialize empty notice to empty entry")
    void serializeEmptyNoticeToEmptyEntry() {
        assertRender(new ConfigEmpty(),
            """
            notice: []
            """
        );
    }

    static class ConfigOneLineChat {
        Notice notice = Notice.chat("Hello world");
    }
    @Test
    @DisplayName("Should serialize simple chat notice to one line entry")
    void serializeSimpleChatNoticeToOneLineEntry() {
        assertRender(new ConfigOneLineChat(),
            """
            notice: "Hello world"
            """
        );
    }

    static class ConfigMultiLineChat {
        Notice notice = Notice.chat("First line", "Second line");
    }
    @Test
    @DisplayName("Should serialize simple chat notice to multiline entry")
    void serializeSimpleChatNoticeToMultilineEntry() {
        assertRender(new ConfigMultiLineChat(),
            """
            notice:
              - "First line"
              - "Second line"
            """);
    }

    static class ConfigSimpleTitle {
        Notice notice = Notice.title("Hello world");
    }
    @Test
    @DisplayName("Should serialize simple title notice to title section")
    void serializeSimpleTitleNoticeToOneLineEntry() {
        assertRender(new ConfigSimpleTitle(),
            """
            notice:
              title: "Hello world"
            """);
    }

    static  class ConfigFullTitle {
        Notice notice = Notice.title("Title", "Subtitle", Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1));
    }
    @Test
    @DisplayName("Should serialize title subtitle with delay notice to title section")
    void serializeTitleSubtitleWithDelayNoticeToOneLineEntry() {
        assertRender(new ConfigFullTitle(),
            """
            notice:
              title: "Title"
              subtitle: "Subtitle"
              times: "1s 2s 1s"
            """);
    }

    static class ConfigSimpleActionBar {
        Notice notice = Notice.actionbar("Hello world");
    }
    @Test
    @DisplayName("Should serialize simple actionbar notice to actionbar section")
    void serializeSimpleActionBarNoticeToOneLineEntry() {
        assertRender(new ConfigSimpleActionBar(),
            """
            notice:
              actionbar: "Hello world"
            """);
    }

    static class ConfigHideTitle {
        Notice notice = Notice.hideTitle();
    }
    @Test
    @DisplayName("Should serialize hide title notice with hide title property")
    void serializeHideTitleNoticeWithHideTitleProperty() {
        assertRender(new ConfigHideTitle(),
            """
            notice:
              titleHide: true
            """);
    }

    static class ConfigSound {
        Notice notice = Notice.sound(Sound.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 1.0f, 1.0f);
    }
    @Test
    @DisplayName("Should serialize sound notice with sound property")
    void serializeSoundNoticeWithSoundProperty() {
        assertRender(new ConfigSound(),
            """
            notice:
              sound: "BLOCK_ANVIL_LAND MASTER 1.0 1.0"
            """);
    }

    static class ConfigSoundWithoutCategory {
        Notice notice = Notice.sound(Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
    }
    @Test
    @DisplayName("Should serialize sound notice without category property")
    void serializeSoundNoticeWithoutCategoryProperty() {
        assertRender(new ConfigSoundWithoutCategory(),
            """
            notice:
              sound: "BLOCK_ANVIL_LAND 1.0 1.0"
            """);
    }

    private void assertRender(Object entity, String expected) {
        String actual = cdn.render(entity).orThrow(exception -> new RuntimeException(exception));

        actual = removeBlankNewLines(actual);
        expected = removeBlankNewLines(expected);

        assertEquals(expected, actual);
    }

    private String removeBlankNewLines(String string) {
        return string
            .replaceAll("\n+", "\n")
            .replaceAll("\n+$", "")
            .replaceAll("^\n+", "");
    }

}
