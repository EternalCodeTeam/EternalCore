package com.eternalcode.core.feature.chat;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class ChatConfig extends OkaeriConfig implements ChatSettings {

    @Comment("# Custom message for unknown command")
    public boolean replaceStandardHelpMessage = false;

    @Comment("# Chat delay to send next message in chat")
    public Duration chatDelay = Duration.ofSeconds(5);

    @Comment("# Number of lines that will be cleared when using the /chat clear command")
    public int linesToClear = 256;

    @Comment("# Chat should be enabled?")
    public boolean chatEnabled = true;

    @Comment({
        "# Sound played when a player sends a chat message.",
        "# To disable, set this to: chatNotice: []"
    })
    public Notice chatNotice = BukkitNotice.sound(Sound.ENTITY_ITEM_PICKUP, 1.8F, 1F);
}
