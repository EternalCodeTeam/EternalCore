package com.eternalcode.core.feature.kit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Base64;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

class KitItemSerializerTest {

    private final KitItemSerializer serializer = new KitItemSerializer();

    @Test
    void shouldSerializeItemsAsVersionedPaperBytes() {
        byte[] sword = { 1, 2, 3 };
        byte[] bread = { 4, 5, 6, 7 };

        String serialized = this.serializer.serialize(List.of(this.item(sword), this.item(bread)));

        assertThat(serialized).isEqualTo("[\"" + Base64.getEncoder().encodeToString(sword) + "\",\"" + Base64.getEncoder().encodeToString(bread) + "\"]");
    }

    @Test
    void shouldSerializeSingleItemAsPlainBase64() {
        byte[] sword = { 9, 8, 7 };

        assertThat(this.serializer.serializeItem(this.item(sword))).isEqualTo(Base64.getEncoder().encodeToString(sword));
    }

    @Test
    void shouldSerializeEmptyList() {
        assertThat(this.serializer.serialize(List.of())).isEqualTo("[]");
        assertThat(this.serializer.deserialize("[]")).isEmpty();
    }

    @Test
    void shouldRejectCorruptedData() {
        assertThatThrownBy(() -> this.serializer.deserialize("items:\n- ==: org.bukkit.inventory.ItemStack"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Corrupted");
    }

    private ItemStack item(byte[] bytes) {
        ItemStack item = mock(ItemStack.class);
        when(item.serializeAsBytes()).thenReturn(bytes);
        return item;
    }
}
