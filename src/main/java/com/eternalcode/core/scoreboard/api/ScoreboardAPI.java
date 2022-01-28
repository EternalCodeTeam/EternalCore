/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.scoreboard.api;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class ScoreboardAPI {

    private static final Map<Class<?>, Field[]> PACKETS = new HashMap<>(8);
    private static final String[] COLOR_CODES;
    private static final VersionType VERSION_TYPE;
    private static final Class<?> CHAT_COMPONENT_CLASS;
    private static final Class<?> CHAT_FORMAT_ENUM;
    private static final Object EMPTY_MESSAGE;
    private static final Object RESET_FORMATTING;
    private static final MethodHandle MESSAGE_FROM_STRING;
    private static final MethodHandle PLAYER_CONNECTION;
    private static final MethodHandle SEND_PACKET;
    private static final MethodHandle PLAYER_GET_HANDLE;
    private static final ScoreboardReflection.PacketConstructor PACKET_SB_OBJ;
    private static final ScoreboardReflection.PacketConstructor PACKET_SB_DISPLAY_OBJ;
    private static final ScoreboardReflection.PacketConstructor PACKET_SB_SCORE;
    private static final ScoreboardReflection.PacketConstructor PACKET_SB_TEAM;
    private static final ScoreboardReflection.PacketConstructor PACKET_SB_SERIALIZABLE_TEAM;
    private static final Class<?> ENUM_SB_HEALTH_DISPLAY;
    private static final Class<?> ENUM_SB_ACTION;
    private static final Object ENUM_SB_HEALTH_DISPLAY_INTEGER;
    private static final Object ENUM_SB_ACTION_CHANGE;
    private static final Object ENUM_SB_ACTION_REMOVE;

    static {
        List<String> list = new ArrayList<>();
        for (ChatColor chatColor : ChatColor.values()) {
            String toString = chatColor.toString();
            list.add(toString);
        }
        COLOR_CODES = list.toArray(new String[0]);
    }

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            VERSION_TYPE = VersionType.V1_17;

            String gameProtocolPackage = "network.protocol.game";
            Class<?> craftPlayerClass = ScoreboardReflection.obcClass("entity.CraftPlayer");
            Class<?> craftChatMessageClass = ScoreboardReflection.obcClass("util.CraftChatMessage");
            Class<?> entityPlayerClass = ScoreboardReflection.nmsClass("server.level", "EntityPlayer");
            Class<?> playerConnectionClass = ScoreboardReflection.nmsClass("server.network", "PlayerConnection");
            Class<?> packetClass = ScoreboardReflection.nmsClass("network.protocol", "Packet");
            Class<?> packetSbObjClass = ScoreboardReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardObjective");
            Class<?> packetSbDisplayObjClass = ScoreboardReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardDisplayObjective");
            Class<?> packetSbScoreClass = ScoreboardReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardScore");
            Class<?> packetSbTeamClass = ScoreboardReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardTeam");
            Class<?> sbTeamClass = VersionType.V1_17.isHigherOrEqual() ? ScoreboardReflection.innerClass(packetSbTeamClass, innerClass -> !innerClass.isEnum()) : null;
            Optional<Field> found = Optional.empty();
            for (Field field1 : entityPlayerClass.getFields()) {
                if (field1.getType().isAssignableFrom(playerConnectionClass)) {
                    found = Optional.of(field1);
                    break;
                }
            }
            Field playerConnectionField = found.orElseThrow(NoSuchFieldException::new);
            Optional<Method> result = Optional.empty();
            for (Method m : playerConnectionClass.getMethods()) {
                if (m.getParameterCount() == 1 && m.getParameterTypes()[0] == packetClass) {
                    result = Optional.of(m);
                    break;
                }
            }
            Method sendPacketMethod = result.orElseThrow(NoSuchMethodException::new);

            MESSAGE_FROM_STRING = lookup.unreflect(craftChatMessageClass.getMethod("fromString", String.class));
            CHAT_COMPONENT_CLASS = ScoreboardReflection.nmsClass("network.chat", "IChatBaseComponent");
            CHAT_FORMAT_ENUM = ScoreboardReflection.nmsClass(null, "EnumChatFormat");
            EMPTY_MESSAGE = Array.get(MESSAGE_FROM_STRING.invoke(""), 0);
            RESET_FORMATTING = ScoreboardReflection.enumValueOf(CHAT_FORMAT_ENUM, "RESET", 21);
            PLAYER_GET_HANDLE = lookup.findVirtual(craftPlayerClass, "getHandle", MethodType.methodType(entityPlayerClass));
            PLAYER_CONNECTION = lookup.unreflectGetter(playerConnectionField);
            SEND_PACKET = lookup.unreflect(sendPacketMethod);
            PACKET_SB_OBJ = ScoreboardReflection.findPacketConstructor(packetSbObjClass, lookup);
            PACKET_SB_DISPLAY_OBJ = ScoreboardReflection.findPacketConstructor(packetSbDisplayObjClass, lookup);
            PACKET_SB_SCORE = ScoreboardReflection.findPacketConstructor(packetSbScoreClass, lookup);
            PACKET_SB_TEAM = ScoreboardReflection.findPacketConstructor(packetSbTeamClass, lookup);
            PACKET_SB_SERIALIZABLE_TEAM = sbTeamClass == null ? null : ScoreboardReflection.findPacketConstructor(sbTeamClass, lookup);

            for (Class<?> clazz : Arrays.asList(packetSbObjClass, packetSbDisplayObjClass, packetSbScoreClass, packetSbTeamClass, sbTeamClass)) {
                if (clazz == null) {
                    continue;
                }
                List<Field> list = new ArrayList<>();
                for (Field field1 : clazz.getDeclaredFields()) {
                    if (!Modifier.isStatic(field1.getModifiers())) {
                        list.add(field1);
                    }
                }
                Field[] fields = list.toArray(new Field[0]);
                for (Field field : fields) {
                    field.setAccessible(true);
                }
                PACKETS.put(clazz, fields);
            }

            String enumSbActionClass = VersionType.V1_13.isHigherOrEqual() ? "ScoreboardServer$Action" : "PacketPlayOutScoreboardScore$EnumScoreboardAction";
            ENUM_SB_HEALTH_DISPLAY = ScoreboardReflection.nmsClass("world.scores.criteria", "IScoreboardCriteria$EnumScoreboardHealthDisplay");
            ENUM_SB_ACTION = ScoreboardReflection.nmsClass("server", enumSbActionClass);
            ENUM_SB_HEALTH_DISPLAY_INTEGER = ScoreboardReflection.enumValueOf(ENUM_SB_HEALTH_DISPLAY, "INTEGER", 0);
            ENUM_SB_ACTION_CHANGE = ScoreboardReflection.enumValueOf(ENUM_SB_ACTION, "CHANGE", 0);
            ENUM_SB_ACTION_REMOVE = ScoreboardReflection.enumValueOf(ENUM_SB_ACTION, "REMOVE", 1);
        } catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }

    private final Player player;
    private final String id;

    private final List<String> lines = new ArrayList<>();
    private String title = ChatColor.RESET.toString();

    private boolean deleted = false;


    public ScoreboardAPI(Player player) {
        this.player = Objects.requireNonNull(player, "player");
        this.id = "sb-" + Integer.toHexString(ThreadLocalRandom.current().nextInt());

        try {
            sendObjectivePacket(ObjectiveMode.CREATE);
            sendDisplayObjectivePacket();
        } catch (Throwable throwable) {
            throw new RuntimeException("Unable to create scoreboard", throwable);
        }
    }


    public String getTitle() {
        return this.title;
    }


    public void updateTitle(String title) {
        if (this.title.equals(title)) {
            return;
        }

        if (!VersionType.V1_13.isHigherOrEqual() && title.length() > 32) {
            throw new IllegalArgumentException("Title is longer than 32 chars");
        }

        this.title = title;

        try {
            sendObjectivePacket(ObjectiveMode.UPDATE);
        } catch (Throwable throwable) {
            throw new RuntimeException("Unable to update scoreboard title", throwable);
        }
    }


    public List<String> getLines() {
        return new ArrayList<>(this.lines);
    }


    public String getLine(int line) {
        checkLineNumber(line, true, false);

        return this.lines.get(line);
    }


    public synchronized void updateLine(int line, String text) {
        checkLineNumber(line, false, true);

        try {
            if (line < size()) {
                this.lines.set(line, text);

                sendTeamPacket(getScoreByLine(line), TeamMode.UPDATE);
                return;
            }

            List<String> newLines = new ArrayList<>(this.lines);

            if (line > size()) {
                for (int i = size(); i < line; i++) {
                    newLines.add("");
                }
            }

            newLines.add(text);

            updateLines(newLines);
        } catch (Throwable throwable) {
            throw new RuntimeException("Unable to update scoreboard lines", throwable);
        }
    }


    public synchronized void removeLine(int line) {
        checkLineNumber(line, false, false);

        if (line >= size()) {
            return;
        }

        List<String> newLines = new ArrayList<>(this.lines);
        newLines.remove(line);
        updateLines(newLines);
    }


    public void updateLines(String... lines) {
        updateLines(Arrays.asList(lines));
    }


    public synchronized void updateLines(Collection<String> lines) {
        checkLineNumber(lines.size(), false, true);

        VersionType.V1_13.isHigherOrEqual();

        List<String> oldLines = new ArrayList<>(this.lines);
        this.lines.clear();
        this.lines.addAll(lines);

        int linesSize = this.lines.size();

        try {
            if (oldLines.size() != linesSize) {
                List<String> oldLinesCopy = new ArrayList<>(oldLines);

                if (oldLines.size() > linesSize) {
                    for (int i = oldLinesCopy.size(); i > linesSize; i--) {
                        sendTeamPacket(i - 1, TeamMode.REMOVE);
                        sendScorePacket(i - 1, ScoreboardAction.REMOVE);

                        oldLines.remove(0);
                    }
                }

                for (int i = oldLinesCopy.size(); i < linesSize; i++) {
                    sendScorePacket(i, ScoreboardAction.CHANGE);
                    sendTeamPacket(i, TeamMode.CREATE);

                    oldLines.add(oldLines.size() - i, getLineByScore(i));
                }
            }

            for (int i = 0; i < linesSize; i++) {
                if (!Objects.equals(getLineByScore(oldLines, i), getLineByScore(i))) {
                    sendTeamPacket(i, TeamMode.UPDATE);
                }
            }
        } catch (Throwable throwable) {
            throw new RuntimeException("Unable to update scoreboard lines", throwable);
        }
    }


    public Player getPlayer() {
        return this.player;
    }


    public String getId() {
        return this.id;
    }


    public boolean isDeleted() {
        return this.deleted;
    }


    public int size() {
        return this.lines.size();
    }


    public void delete() {
        try {
            for (int i = 0; i < this.lines.size(); i++) {
                sendTeamPacket(i, TeamMode.REMOVE);
            }

            sendObjectivePacket(ObjectiveMode.REMOVE);
        } catch (Throwable throwable) {
            throw new RuntimeException("Unable to delete scoreboard", throwable);
        }

        this.deleted = true;
    }


    protected boolean hasLinesMaxLength() {
        return !VersionType.V1_13.isHigherOrEqual();
    }

    private void checkLineNumber(int line, boolean checkInRange, boolean checkMax) {
        if (line < 0) {
            throw new IllegalArgumentException("Line number must be positive");
        }

        if (checkInRange && line >= this.lines.size()) {
            throw new IllegalArgumentException("Line number must be under " + this.lines.size());
        }

        if (checkMax && line >= COLOR_CODES.length - 1) {
            throw new IllegalArgumentException("Line number is too high: " + line);
        }
    }

    private int getScoreByLine(int line) {
        return this.lines.size() - line - 1;
    }

    private String getLineByScore(int score) {
        return getLineByScore(this.lines, score);
    }

    private String getLineByScore(List<String> lines, int score) {
        return lines.get(lines.size() - score - 1);
    }

    private void sendObjectivePacket(ObjectiveMode mode) throws Throwable {
        Object packet = PACKET_SB_OBJ.invoke();

        setField(packet, String.class, this.id);
        setField(packet, int.class, mode.ordinal());

        if (mode != ObjectiveMode.REMOVE) {
            setComponentField(packet, this.title, 1);

            if (VersionType.V1_8.isHigherOrEqual()) {
                setField(packet, ENUM_SB_HEALTH_DISPLAY, ENUM_SB_HEALTH_DISPLAY_INTEGER);
            }
        }

        sendPacket(packet);
    }

    private void sendDisplayObjectivePacket() throws Throwable {
        Object packet = PACKET_SB_DISPLAY_OBJ.invoke();

        setField(packet, int.class, 1);
        setField(packet, String.class, this.id);

        sendPacket(packet);
    }

    private void sendScorePacket(int score, ScoreboardAction action) throws Throwable {
        Object packet = PACKET_SB_SCORE.invoke();

        setField(packet, String.class, COLOR_CODES[score], 0);

        if (VersionType.V1_8.isHigherOrEqual()) {
            setField(packet, ENUM_SB_ACTION, action == ScoreboardAction.REMOVE ? ENUM_SB_ACTION_REMOVE : ENUM_SB_ACTION_CHANGE);
        }

        if (action == ScoreboardAction.CHANGE) {
            setField(packet, String.class, this.id, 1);
            setField(packet, int.class, score);
        }

        sendPacket(packet);
    }

    private void sendTeamPacket(int score, TeamMode mode) throws Throwable {
        if (mode == TeamMode.ADD_PLAYERS || mode == TeamMode.REMOVE_PLAYERS) {
            throw new UnsupportedOperationException();
        }

        int maxLength = hasLinesMaxLength() ? 16 : 1024;
        Object packet = PACKET_SB_TEAM.invoke();

        setField(packet, String.class, this.id + ':' + score);
        setField(packet, int.class, mode.ordinal(), VERSION_TYPE == VersionType.V1_8 ? 1 : 0);

        if (mode == TeamMode.CREATE || mode == TeamMode.UPDATE) {
            String line = getLineByScore(score);
            String prefix;
            String suffix = null;

            if (line == null || line.isEmpty()) {
                prefix = COLOR_CODES[score] + ChatColor.RESET;
            } else if (line.length() <= maxLength) {
                prefix = line;
            } else {

                int index = line.charAt(maxLength - 1) == ChatColor.COLOR_CHAR ? (maxLength - 1) : maxLength;
                prefix = line.substring(0, index);
                String suffixTmp = line.substring(index);
                ChatColor chatColor = null;

                if (suffixTmp.length() >= 2 && suffixTmp.charAt(0) == ChatColor.COLOR_CHAR) {
                    chatColor = ChatColor.getByChar(suffixTmp.charAt(1));
                }

                String color = ChatColor.getLastColors(prefix);
                boolean addColor = chatColor == null || chatColor.isFormat();

                suffix = (addColor ? (color.isEmpty() ? ChatColor.RESET.toString() : color) : "") + suffixTmp;
            }

            if (prefix.length() > maxLength || (suffix != null && suffix.length() > maxLength)) {

                prefix = prefix.substring(0, maxLength);
                suffix = (suffix != null) ? suffix.substring(0, maxLength) : null;
            }

            if (VersionType.V1_17.isHigherOrEqual()) {
                Object team = PACKET_SB_SERIALIZABLE_TEAM.invoke();

                setComponentField(team, "", 0);
                setField(team, CHAT_FORMAT_ENUM, RESET_FORMATTING);
                setComponentField(team, prefix, 1);
                setComponentField(team, suffix == null ? "" : suffix, 2);
                setField(team, String.class, "always", 0);
                setField(team, String.class, "always", 1);
                setField(packet, Optional.class, Optional.of(team));
            }

            if (mode == TeamMode.CREATE) {
                setField(packet, Collection.class, Collections.singletonList(COLOR_CODES[score]));
            }
        }

        sendPacket(packet);
    }

    private void sendPacket(Object packet) throws Throwable {
        if (this.deleted) {
            throw new IllegalStateException("This scoreboard is deleted");
        }

        if (this.player.isOnline()) {
            Object entityPlayer = PLAYER_GET_HANDLE.invoke(this.player);
            Object playerConnection = PLAYER_CONNECTION.invoke(entityPlayer);
            SEND_PACKET.invoke(playerConnection, packet);
        }
    }

    private void setField(Object object, Class<?> fieldType, Object value) throws ReflectiveOperationException {
        setField(object, fieldType, value, 0);
    }

    private void setField(Object packet, Class<?> fieldType, Object value, int count) throws ReflectiveOperationException {
        int i = 0;
        for (Field field : PACKETS.get(packet.getClass())) {
            if (field.getType() == fieldType && count == i++) {
                field.set(packet, value);
            }
        }
    }

    private void setComponentField(Object packet, String value, int count) throws Throwable {
        if (!VersionType.V1_13.isHigherOrEqual()) {
            setField(packet, String.class, value, count);
            return;
        }

        int i = 0;
        for (Field field : PACKETS.get(packet.getClass())) {
            if ((field.getType() == String.class || field.getType() == CHAT_COMPONENT_CLASS) && count == i++) {
                field.set(packet, value.isEmpty() ? EMPTY_MESSAGE : Array.get(MESSAGE_FROM_STRING.invoke(value), 0));
            }
        }
    }

    enum ObjectiveMode {
        CREATE, REMOVE, UPDATE
    }

    enum TeamMode {
        CREATE, REMOVE, UPDATE, ADD_PLAYERS, REMOVE_PLAYERS
    }

    enum ScoreboardAction {
        CHANGE, REMOVE
    }

    enum VersionType {
        V1_8, V1_13, V1_17;

        public boolean isHigherOrEqual() {
            return VERSION_TYPE.ordinal() >= ordinal();
        }
    }
}
