package com.eternalcode.core.database.persister;

import com.eternalcode.commons.bukkit.position.Position;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;

public class PositionPersister extends BaseDataType {

    private static final PositionPersister instance = new PositionPersister();

    private PositionPersister() {
        super(SqlType.LONG_STRING, new Class<?>[] {Position.class});
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        if (javaObject == null) {
            return null;
        }

        Position position = (Position) javaObject;
        return position.world() + "/" + position.x() + "/" + position.y() + "/" + position.z() + "/" + position.yaw() + "/" + position.pitch();
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return String.valueOf(defaultStr);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        String s = (String) sqlArg;

        if (s == null) {
            return null;
        }

        String[] params = s.split("/");

        if (params.length != 6) {
            throw new IllegalArgumentException("Invalid position format: " + s);
        }

        return new Position(
            Double.parseDouble(params[1]),
            Double.parseDouble(params[2]),
            Double.parseDouble(params[3]),
            Float.parseFloat(params[4]),
            Float.parseFloat(params[5]),
            params[0]
        );
    }

    public static PositionPersister getSingleton() {
        return instance;
    }
}
