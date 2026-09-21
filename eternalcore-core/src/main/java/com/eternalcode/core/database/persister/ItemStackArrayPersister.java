package com.eternalcode.core.database.persister;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import org.bukkit.inventory.ItemStack;

// Paper only added serializeItemsAsBytes in 1.21.1, so we encode item by item to stay usable on 1.19.3.
// The per item serializeAsBytes we call here differs by one word, easy to mix the two up.
public class ItemStackArrayPersister extends BaseDataType {

    private static final ItemStackArrayPersister INSTANCE = new ItemStackArrayPersister();

    private static final byte FORMAT_VERSION = 2;
    private static final int EMPTY_SLOT = -1;
    private static final int MAX_SLOTS = 1024;

    private ItemStackArrayPersister() {
        super(SqlType.BYTE_ARRAY, new Class<?>[] { ItemStack[].class });
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return encode((ItemStack[]) javaObject);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getBytes(columnPos);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return decode((byte[]) sqlArg);
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) throws SQLException {
        throw new SQLException("Items cannot be given a default value");
    }

    @Override
    public boolean isArgumentHolderRequired() {
        return true;
    }

    @Override
    public Class<?> getPrimaryClass() {
        return ItemStack[].class;
    }

    public static ItemStackArrayPersister getSingleton() {
        return INSTANCE;
    }

    private static byte[] encode(ItemStack[] items) {
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
            throw new IllegalStateException("Failed to encode items", exception);
        }

        return buffer.toByteArray();
    }

    private static ItemStack[] decode(byte[] bytes) {
        if (bytes == null) {
            return new ItemStack[0];
        }

        if (bytes.length == 0) {
            throw new IllegalStateException("Stored items are empty, the row is corrupted");
        }

        try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes))) {
            byte version = input.readByte();
            if (version != FORMAT_VERSION) {
                throw new IllegalStateException("Unknown item format: " + version);
            }

            int slots = input.readInt();
            if (slots < 0 || slots > MAX_SLOTS) {
                throw new IllegalStateException("Item array declares " + slots + " slots");
            }

            ItemStack[] items = new ItemStack[slots];
            for (int slot = 0; slot < slots; slot++) {
                int length = input.readInt();
                if (length == EMPTY_SLOT) {
                    continue;
                }

                if (length < 0 || length > bytes.length) {
                    throw new IllegalStateException("Slot " + slot + " declares " + length + " bytes");
                }

                byte[] serialized = new byte[length];
                input.readFully(serialized);
                items[slot] = ItemStack.deserializeBytes(serialized);
            }

            return items;
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed to decode items", exception);
        }
    }
}
