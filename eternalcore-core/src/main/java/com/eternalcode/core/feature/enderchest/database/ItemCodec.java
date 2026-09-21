package com.eternalcode.core.feature.enderchest.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.inventory.ItemStack;

// Paper only added serializeItemsAsBytes in 1.21.1, so we encode item by item to stay usable on 1.19.3.
// The per item serializeAsBytes we call here differs by one word, easy to mix the two up.
final class ItemCodec {

    private static final byte FORMAT_VERSION = 2;
    private static final int EMPTY_SLOT = -1;
    private static final int MAX_SLOTS = 1024;

    private ItemCodec() {}

    static byte[] encode(ItemStack[] items) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (DataOutputStream output = new DataOutputStream(buffer)) {
            output.writeByte(FORMAT_VERSION);
            output.writeInt(items.length);

            for (ItemStack item : items) {
                if (item == null) {
                    output.writeInt(EMPTY_SLOT);
                    continue;
                }

                byte[] serialized = item.serializeAsBytes();
                output.writeInt(serialized.length);
                output.write(serialized);
            }
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed to encode ender chest items", exception);
        }

        return buffer.toByteArray();
    }

    static ItemStack[] decode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return new ItemStack[0];
        }

        try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes))) {
            byte version = input.readByte();
            if (version != FORMAT_VERSION) {
                throw new IllegalStateException("Unknown ender chest item format: " + version);
            }

            int slots = input.readInt();
            if (slots < 0 || slots > MAX_SLOTS) {
                throw new IllegalStateException("Ender chest page declares " + slots + " slots");
            }

            ItemStack[] items = new ItemStack[slots];
            for (int slot = 0; slot < slots; slot++) {
                int length = input.readInt();
                if (length == EMPTY_SLOT) {
                    continue;
                }

                if (length < 0 || length > bytes.length) {
                    throw new IllegalStateException("Ender chest slot " + slot + " declares " + length + " bytes");
                }

                byte[] serialized = new byte[length];
                input.readFully(serialized);
                items[slot] = ItemStack.deserializeBytes(serialized);
            }

            return items;
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed to decode ender chest items", exception);
        }
    }
}
