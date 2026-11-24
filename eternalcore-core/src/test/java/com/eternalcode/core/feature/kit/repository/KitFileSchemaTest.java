package com.eternalcode.core.feature.kit.repository;

import static com.eternalcode.core.feature.kit.KitTestFactory.kit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitItemSerializer;
import com.eternalcode.core.feature.kit.KitRequirements;
import com.eternalcode.core.feature.kit.KitTestFactory;
import com.eternalcode.core.feature.kit.KitType;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.bukkit.GameMode;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KitFileSchemaTest {

    private KitFileSchema schema;

    @BeforeEach
    void setUp() {
        ItemStack deserialized = KitTestFactory.item();
        KitItemSerializer serializer = mock(KitItemSerializer.class);
        when(serializer.serializeItem(any(ItemStack.class))).thenReturn("BASE64");
        when(serializer.deserializeItem("BASE64")).thenReturn(deserialized);

        this.schema = new KitFileSchema(serializer);
    }

    @Test
    void shouldStoreItemsAsBase64AndKeepOtherFieldsEditable() {
        Kit kit = kit("vip")
            .displayName("<gold>VIP")
            .description(List.of("<gray>Line 1", "<gray>Line 2"))
            .type(KitType.RECURRING)
            .cooldown(Duration.ofHours(12))
            .requirements(KitRequirements.of(Set.of("world"), Set.of(GameMode.SURVIVAL), Duration.ofHours(5)))
            .commands(List.of("eco give {PLAYER} 100"))
            .items(List.of(KitTestFactory.item(), KitTestFactory.item()))
            .slot(4)
            .build();

        YamlConfiguration yaml = this.schema.write(kit);

        assertThat(yaml.getString("icon")).isEqualTo("BASE64");
        assertThat(yaml.getStringList("items")).containsExactly("BASE64", "BASE64");
        assertThat(yaml.getString("display-name")).isEqualTo("<gold>VIP");
        assertThat(yaml.getString("cooldown")).isEqualTo("12h");
        assertThat(yaml.getStringList("requirements.game-modes")).containsExactly("SURVIVAL");
    }

    @Test
    void shouldReadWhatWasWritten() throws InvalidConfigurationException {
        Kit kit = kit("starter")
            .type(KitType.FIRST_JOIN)
            .description(List.of("<gray>For new players"))
            .requirements(KitRequirements.of(Set.of("world", "world_nether"), Set.of(GameMode.SURVIVAL), Duration.ofMinutes(30)))
            .commands(List.of("say {PLAYER}"))
            .items(List.of(KitTestFactory.item()))
            .slot(2)
            .build();

        YamlConfiguration saved = new YamlConfiguration();
        saved.loadFromString(this.schema.write(kit).saveToString());

        Kit read = this.schema.read("starter", saved);

        assertThat(read.type()).isEqualTo(KitType.FIRST_JOIN);
        assertThat(read.description()).containsExactly("<gray>For new players");
        assertThat(read.permission()).isEqualTo(kit.permission());
        assertThat(read.requirements()).isEqualTo(kit.requirements());
        assertThat(read.commands()).containsExactly("say {PLAYER}");
        assertThat(read.items()).hasSize(1);
        assertThat(read.slot()).isEqualTo(2);
    }

    @Test
    void shouldReadMinimalFileWithDefaults() throws InvalidConfigurationException {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.loadFromString("permission: eternalcore.kits.daily\nicon: BASE64\n");

        Kit read = this.schema.read("daily", yaml);

        assertThat(read.type()).isEqualTo(KitType.RECURRING);
        assertThat(read.cooldown()).isEqualTo(Duration.ZERO);
        assertThat(read.requirements()).isEqualTo(KitRequirements.none());
        assertThat(read.displayName()).isEqualTo("daily");
        assertThat(read.items()).isEmpty();
    }

    @Test
    void shouldRejectUnknownTypeWithKitName() throws InvalidConfigurationException {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.loadFromString("permission: p\nicon: BASE64\ntype: WEEKLY\n");

        assertThatThrownBy(() -> this.schema.read("broken", yaml))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("broken")
            .hasMessageContaining("WEEKLY");
    }
}
