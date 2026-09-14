package com.eternalcode.core.database.persister;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class InstantPersister extends BaseDataType {

    public InstantPersister(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String s) throws SQLException {
        return null;
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults databaseResults, int i) throws SQLException {
        return null;
    }
}
