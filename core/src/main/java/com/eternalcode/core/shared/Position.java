package com.eternalcode.core.shared;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Disclaimer - Bukkit {@link org.bukkit.Location} storage may cause a memory leak, because it is a wrapper for
 * coordinates and {@link org.bukkit.World} reference. If you need to store location use {@link Position} and
 * {@link PositionAdapter}.
 * */
public final class Position {

    public final static String NONE_WORLD = "__NONE__";

    private final static Pattern PARSE_FORMAT = Pattern.compile("Position\\{x=(?<x>-?[\\d.]+), y=(?<y>-?[\\d.]+), z=(?<z>-?[\\d.]+), yaw=(?<yaw>-?[\\d.]+), pitch=(?<pitch>-?[\\d.]+), world='(?<world>.+)'}");

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final String world;


    public Position(double x, double y, double z, float yaw, float pitch, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public String getWorld() {
        return this.world;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isNoneWorld() {
        return this.world.equals(NONE_WORLD);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position position = (Position) o;

        return Double.compare(position.x, this.x) == 0
            && Double.compare(position.y, this.y) == 0
            && Double.compare(position.z, this.z) == 0
            && Float.compare(position.yaw, this.yaw) == 0
            && Float.compare(position.pitch, this.pitch) == 0
            && this.world.equals(position.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    @Override
    public String toString() {
        return "Position{" +
            "x=" + this.x +
            ", y=" + this.y +
            ", z=" + this.z +
            ", yaw=" + this.yaw +
            ", pitch=" + this.pitch +
            ", world='" + this.world + '\'' +
            '}';
    }

    public static Position parse(String parse) {
        Matcher matcher = PARSE_FORMAT.matcher(parse);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid position format: " + parse);
        }

        return new Position(
            Double.parseDouble(matcher.group("x")),
            Double.parseDouble(matcher.group("y")),
            Double.parseDouble(matcher.group("z")),
            Float.parseFloat(matcher.group("yaw")),
            Float.parseFloat(matcher.group("pitch")),
            matcher.group("world")
        );
    }
}
