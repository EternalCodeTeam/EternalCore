package com.eternalcode.core.feature.automessage.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

@Getter
@Accessors(fluent = true)
public class ENAutoMessageMessages extends OkaeriConfig implements AutoMessageMessages {

    @Comment({
        "",
        "# If you want to use placeholder %server_online% you need to install",
        "# PlaceholderAPI plugin and download placeholders for Server",
        "# like /papi ecloud download Server",
    })
    public Map<String, Notice> messages = Map.of(
        "1", BukkitNotice.builder()
            .actionBar("<dark_gray>» <gold>There are <white>%server_online% <gold>people online on the server!")
            .sound(Sound.ITEM_ARMOR_EQUIP_IRON, 1.0f, 1.0f)
            .build(),

        "2", BukkitNotice.builder()
            .chat("<dark_gray>» <gold>You need help from an admin?")
            .chat("<dark_gray>» <gold>Type command <white>/helpop <gold>to ask!")
            .chat("<dark_gray>» <color:#9d6eef><click:suggest_command:'/helpop'>Click to execute!</click></color:#9d6eef>")
            .sound(Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.0f)
            .build()
    );

    Notice enabled = Notice.chat("<color:#9d6eef>► <white>Enabled auto messages!");
    Notice disabled = Notice.chat("<color:#9d6eef>► <white>Disabled auto messages!");

    @Override
    public Collection<Notice> messages() {
        return this.messages.values();
    }

    @Override
    public Notice enabled() {
        return this.enabled;
    }

    @Override
    public Notice disabled() {
        return this.disabled;
    }
}
