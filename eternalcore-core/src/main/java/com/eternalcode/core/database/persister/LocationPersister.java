package com.eternalcode.core.database.persister;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationPersister extends BaseDataType {

    private static final LocationPersister instance = new LocationPersister();

    private LocationPersister() {
        super(SqlType.LONG_STRING, new Class<?>[] {LocationPersister.class});
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        if (javaObject == null) {
            return null;
        }

        Location loc = (Location) javaObject;
        String worldName = "world";

        if (loc.getWorld() != null) {
            worldName = loc.getWorld().getName();
        }

        return worldName + "/" + loc.getX() + "/" + loc.getY() + "/" + loc.getZ() + "/" + loc.getYaw() + "/" + loc.getPitch();
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

        return new Location(
            Bukkit.getWorld(params[0]),
            Double.parseDouble(params[1]),
            Double.parseDouble(params[2]),
            Double.parseDouble(params[3]),
            Float.parseFloat(params[4]),
            Float.parseFloat(params[5])
        );
    }

    public static LocationPersister getSingleton() {
        return instance;
    }

}
