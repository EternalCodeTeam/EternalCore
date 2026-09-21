package com.eternalcode.core.feature.enderchest.database;

import com.eternalcode.core.feature.enderchest.PageContents;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = EnderchestPageTable.TABLE_NAME)
class EnderchestPageTable {

    static final String TABLE_NAME = "eternal_core_enderchests";
    static final String OWNER_COLUMN = "owner";
    static final String PAGE_COLUMN = "page";
    static final String CONTENTS_COLUMN = "contents";

    @DatabaseField(columnName = "id", id = true)
    private String id;

    @DatabaseField(columnName = OWNER_COLUMN, index = true)
    private UUID ownerUniqueId;

    @DatabaseField(columnName = PAGE_COLUMN)
    private int page;

    @DatabaseField(columnName = CONTENTS_COLUMN, dataType = DataType.BYTE_ARRAY)
    private byte[] contents;

    EnderchestPageTable() {}

    private EnderchestPageTable(UUID ownerUniqueId, int page, byte[] contents) {
        this.id = idOf(ownerUniqueId, page);
        this.ownerUniqueId = ownerUniqueId;
        this.page = page;
        this.contents = contents;
    }

    static String idOf(UUID ownerUniqueId, int page) {
        return ownerUniqueId + ":" + page;
    }

    static EnderchestPageTable from(UUID ownerUniqueId, PageContents contents) {
        return new EnderchestPageTable(ownerUniqueId, contents.page(), ItemCodec.encode(contents.items()));
    }

    PageContents toContents() {
        return new PageContents(this.page, ItemCodec.decode(this.contents));
    }
}
