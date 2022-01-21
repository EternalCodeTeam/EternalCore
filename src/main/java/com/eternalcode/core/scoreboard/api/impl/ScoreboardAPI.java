package com.eternalcode.core.scoreboard.api.impl;

import com.eternalcode.core.scoreboard.api.enums.ObjectiveMode;
import com.eternalcode.core.scoreboard.api.enums.ScoreboardAction;
import com.eternalcode.core.scoreboard.api.enums.TeamMode;
import com.eternalcode.core.scoreboard.api.enums.VersionType;
import com.eternalcode.core.scoreboard.api.interfances.PacketConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
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

public final class ScoreboardAPI {

    public static final Map<Class<?>, Field[]> PACKETS = new HashMap<>(8);
    private static final String[] COLOR_CODES = Arrays.stream(ChatColor.values()).map(Object::toString).toArray(String[]::new);
    private static VersionType VERSION_TYPE;
    private static Class<?> CHAT_COMPONENT_CLASS;
    private static Class<?> CHAT_FORMAT_ENUM;
    private static Object EMPTY_MESSAGE;
    private static Object RESET_FORMATTING;
    private static MethodHandle MESSAGE_FROM_STRING;
    private static MethodHandle PLAYER_CONNECTION;
    private static MethodHandle SEND_PACKET;
    private static MethodHandle PLAYER_GET_HANDLE;
    private static PacketConstructor PACKET_SB_OBJ;
    private static PacketConstructor PACKET_SB_DISPLAY_OBJ;
    private static PacketConstructor PACKET_SB_SCORE;
    private static PacketConstructor PACKET_SB_TEAM;
    private static PacketConstructor PACKET_SB_SERIALIZABLE_TEAM;
    private static Class<?> ENUM_SB_HEALTH_DISPLAY;
    private static Class<?> ENUM_SB_ACTION;
    private static Object ENUM_SB_HEALTH_DISPLAY_INTEGER;
    private static Object ENUM_SB_ACTION_CHANGE;
    private static Object ENUM_SB_ACTION_REMOVE;
    private final Player player;
    private final String id;
    private final List<String> lines = new ArrayList<>();
    private String title = ChatColor.RESET.toString();
    private boolean deleted = false;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            if (ScoreboardReflection.isRepackaged()) {
                VERSION_TYPE = VersionType.V1_17;
            } else if (ScoreboardReflection.nmsOptionalClass(null, "ScoreboardServer$Action").isPresent()) {
                VERSION_TYPE = VersionType.V1_13;
            } else if (ScoreboardReflection.nmsOptionalClass(null, "IScoreboardCriteria$EnumScoreboardHealthDisplay").isPresent()) {
                VERSION_TYPE = VersionType.V1_8;
            } else {
                VERSION_TYPE = VersionType.V1_7;
            }

            String gameProtocolPackage = "network.protocol.game";
            Class<?> craftChatMessageClass = ScoreboardReflection.obcClass("util.CraftChatMessage");
            Class<?> entityPlayerClass = ScoreboardReflection.nmsClass("server.level", "EntityPlayer");
            Class<?> playerConnectionClass = ScoreboardReflection.nmsClass("server.network", "PlayerConnection");
            Class<?> packetClass = ScoreboardReflection.nmsClass("network.protocol", "Packet");
            Class<?> packetSbObjClass = ScoreboardReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardObjective");
            Class<?> packetSbDisplayObjClass = ScoreboardReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardDisplayObjective");
            Class<?> packetSbScoreClass = ScoreboardReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardScore");
            Class<?> packetSbTeamClass = ScoreboardReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardTeam");
            Class<?> sbTeamClass = VersionType.V1_17.isHigherOrEqual() ? ScoreboardReflection.innerClass(packetSbTeamClass, innerClass -> !innerClass.isEnum()) : null;
            Field playerConnectionField = Arrays.stream(entityPlayerClass.getFields()).filter(field -> field.getType().isAssignableFrom(playerConnectionClass)).findFirst().orElseThrow(NoSuchFieldException::new);
            Method sendPacketMethod = Arrays.stream(playerConnectionClass.getMethods()).filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == packetClass).findFirst().orElseThrow(NoSuchMethodException::new);
            MESSAGE_FROM_STRING = lookup.unreflect(craftChatMessageClass.getMethod("fromString", String.class));
            CHAT_COMPONENT_CLASS = ScoreboardReflection.nmsClass("network.chat", "IChatBaseComponent");
            CHAT_FORMAT_ENUM = ScoreboardReflection.nmsClass(null, "EnumChatFormat");
            EMPTY_MESSAGE = Array.get(MESSAGE_FROM_STRING.invoke(""), 0);
            RESET_FORMATTING = ScoreboardReflection.enumValueOf(CHAT_FORMAT_ENUM, "RESET", 21);
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

                Field[] fields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers())).toArray(Field[]::new);

                for (Field field : fields) {
                    field.setAccessible(true);
                }

                PACKETS.put(clazz, fields);
            }

            if (VersionType.V1_8.isHigherOrEqual()) {
                String enumSbActionClass = VersionType.V1_13.isHigherOrEqual() ? "ScoreboardServer$Action" : "PacketPlayOutScoreboardScore$EnumScoreboardAction";
                ENUM_SB_HEALTH_DISPLAY = ScoreboardReflection.nmsClass("world.scores.criteria", "IScoreboardCriteria$EnumScoreboardHealthDisplay");
                ENUM_SB_ACTION = ScoreboardReflection.nmsClass("server", enumSbActionClass);
                ENUM_SB_ACTION_CHANGE = ScoreboardReflection.enumValueOf(ENUM_SB_ACTION, "CHANGE", 0);
                ENUM_SB_ACTION_REMOVE = ScoreboardReflection.enumValueOf(ENUM_SB_ACTION, "REMOVE", 1);
            } else {
                ENUM_SB_HEALTH_DISPLAY = null;
                ENUM_SB_ACTION = null;
                ENUM_SB_ACTION_CHANGE = null;
                ENUM_SB_ACTION_REMOVE = null;
            }
        } catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }

    public ScoreboardAPI(Player player) {
        this.player = player;
        id = "fb-" + Integer.toHexString(ThreadLocalRandom.current().nextInt());

        try {
            sendObjectivePacket(ObjectiveMode.CREATE);
            sendDisplayObjectivePacket();
        } catch (Throwable t) {
            throw new RuntimeException("Unable to create scoreboard", t);
        }
    }

    private static boolean hasLinesMaxLength() {
        return !VersionType.V1_13.isHigherOrEqual();
    }

    private static String getLineByScore(List<String> lines, int score) {
        return lines.get(lines.size() - score - 1);
    }

    private static void setField(Object object, Class<?> fieldType, Object value) throws ReflectiveOperationException {
        setField(object, fieldType, value, 0);
    }

    private static void setField(Object packet, Class<?> fieldType, Object value, int count) throws ReflectiveOperationException {
        int i = 0;
        for (Field field : PACKETS.get(packet.getClass())) {
            if (field.getType() == fieldType && count == i++) {
                field.set(packet, value);
            }
        }
    }

    private static void setComponentField(Object packet, String value, int count) throws Throwable {
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

    public static void setPlayerGetHandle(MethodHandle playerGetHandle) {
        PLAYER_GET_HANDLE = playerGetHandle;
    }

    public static void setEnumSbHealthDisplayInteger(Object enumSbHealthDisplayInteger) {
        ENUM_SB_HEALTH_DISPLAY_INTEGER = enumSbHealthDisplayInteger;
    }

    public void updateTitle(String title) {
        if (title.equals(title)) {
            return;
        }

        if (!VersionType.V1_13.isHigherOrEqual() && title.length() > 32) {
            throw new IllegalArgumentException("Title is longer than 32 chars");
        }

        title = title;

        try {
            sendObjectivePacket(ObjectiveMode.UPDATE);
        } catch (Throwable t) {
            throw new RuntimeException("Unable to update scoreboard title", t);
        }
    }

    public List<String> getLines() {
        return new ArrayList<>(lines);
    }

    public String getLine(int line) {
        checkLineNumber(line, true, false);

        return lines.get(line);
    }

    public synchronized void updateLine(int line, String text) {
        checkLineNumber(line, false, true);

        try {
            if (line < getSize()) {
                lines.set(line, text);

                sendTeamPacket(getScoreByLine(line), TeamMode.UPDATE);
                return;
            }

            List<String> newLines = new ArrayList<>(lines);

            if (line > getSize()) {
                for (int i = getSize(); i < line; i++) {
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

        if (line >= getSize()) {
            return;
        }

        List<String> newLines = new ArrayList<>(lines);
        newLines.remove(line);
        updateLines(newLines);
    }

    public void updateLines(String... lines) {
        updateLines(Arrays.asList(lines));
    }

    public synchronized void updateLines(Collection<String> lines) {
        checkLineNumber(lines.size(), false, true);

        if (!VersionType.V1_13.isHigherOrEqual()) {
            int lineCount = 0;
            for (String s : lines) {
                if (s != null && s.length() > 30) {
                    throw new IllegalArgumentException("Line " + lineCount + " is longer than 30 chars");
                }
                lineCount++;
            }
        }

        List<String> oldLines = new ArrayList<>(lines);
        lines.clear();
        lines.addAll(lines);

        int linesSize = lines.size();

        try {
            if (oldLines.size() != linesSize) {
                List<String> oldLinesCopy = new ArrayList<>(oldLines);

                if (oldLines.size() > linesSize) {
                    for (int i = oldLinesCopy.size(); i > linesSize; i--) {
                        sendTeamPacket(i - 1, TeamMode.REMOVE);
                        sendScorePacket(i - 1, ScoreboardAction.REMOVE);

                        oldLines.remove(0);
                    }
                } else {
                    for (int i = oldLinesCopy.size(); i < linesSize; i++) {
                        sendScorePacket(i, ScoreboardAction.CHANGE);
                        sendTeamPacket(i, TeamMode.CREATE);

                        oldLines.add(oldLines.size() - i, getLineByScore(i));
                    }
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

    public int getSize() {
        return lines.size();
    }

    public void delete() {
        try {
            for (int i = 0; i < lines.size(); i++) {
                sendTeamPacket(i, TeamMode.REMOVE);
            }

            sendObjectivePacket(ObjectiveMode.REMOVE);
        } catch (Throwable throwable) {
            throw new RuntimeException("Unable to delete scoreboard", throwable);
        }

        deleted = true;
    }

    private void checkLineNumber(int line, boolean checkInRange, boolean checkMax) {
        if (line < 0) {
            throw new IllegalArgumentException("Line number must be positive");
        }

        if (checkInRange && line >= lines.size()) {
            throw new IllegalArgumentException("Line number must be under " + lines.size());
        }

        if (checkMax && line >= COLOR_CODES.length - 1) {
            throw new IllegalArgumentException("Line number is too high: " + line);
        }
    }

    private int getScoreByLine(int line) {
        return lines.size() - line - 1;
    }

    private String getLineByScore(int score) {
        return getLineByScore(lines, score);
    }

    private void sendObjectivePacket(ObjectiveMode mode) throws Throwable {
        Object packet = PACKET_SB_OBJ.invoke();

        setField(packet, String.class, id);
        setField(packet, int.class, mode.ordinal());

        if (mode != ObjectiveMode.REMOVE) {
            setComponentField(packet, title, 1);

            if (VersionType.V1_8.isHigherOrEqual()) {
                setField(packet, ENUM_SB_HEALTH_DISPLAY, ENUM_SB_HEALTH_DISPLAY_INTEGER);
            }
        }

        if (VERSION_TYPE == VersionType.V1_7) {
            setField(packet, String.class, "", 1);
        }

        sendPacket(packet);
    }

    private void sendDisplayObjectivePacket() throws Throwable {
        Object packet = PACKET_SB_DISPLAY_OBJ.invoke();

        setField(packet, int.class, 1);
        setField(packet, String.class, id);

        sendPacket(packet);
    }

    private void sendScorePacket(int score, ScoreboardAction action) throws Throwable {
        Object packet = PACKET_SB_SCORE.invoke();

        setField(packet, String.class, COLOR_CODES[score], 0);

        if (VersionType.V1_8.isHigherOrEqual()) {
            setField(packet, ENUM_SB_ACTION, action == ScoreboardAction.REMOVE ? ENUM_SB_ACTION_REMOVE : ENUM_SB_ACTION_CHANGE);
        } else {
            setField(packet, int.class, action.ordinal(), 1);
        }

        if (action == ScoreboardAction.CHANGE) {
            setField(packet, String.class, id, 1);
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

        setField(packet, String.class, id + ':' + score);
        setField(packet, int.class, mode.ordinal(), VERSION_TYPE == VersionType.V1_8 ? 1 : 0);

        if (mode == TeamMode.CREATE || mode == TeamMode.UPDATE) {
            String line = getLineByScore(score);
            String prefix;
            String suffix;

            if (line == null || line.isEmpty()) {
                prefix = COLOR_CODES[score] + ChatColor.RESET;
            }

            if (line != null && line.length() <= maxLength) {
                prefix = line;
            }

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

            if (suffix.length() > maxLength) {

                prefix = prefix.substring(0, maxLength);
                suffix = suffix.substring(0, maxLength);
            }

            if (VersionType.V1_17.isHigherOrEqual()) {
                Object team = PACKET_SB_SERIALIZABLE_TEAM.invoke();

                setComponentField(team, "", 0);
                setField(team, CHAT_FORMAT_ENUM, RESET_FORMATTING);
                setComponentField(team, prefix, 1);
                setComponentField(team, suffix, 2);
                setField(team, String.class, "always", 0);
                setField(team, String.class, "always", 1);
                setField(packet, Optional.class, Optional.of(team));
            }

            setComponentField(packet, prefix, 2);
            setComponentField(packet, suffix, 3);
            setField(packet, String.class, "always", 4);
            setField(packet, String.class, "always", 5);

            if (mode == TeamMode.CREATE) {
                setField(packet, Collection.class, Collections.singletonList(COLOR_CODES[score]));
            }
        }

        sendPacket(packet);
    }

    private void sendPacket(Object packet) throws Throwable {
        if (deleted) {
            throw new IllegalStateException("This scoreboard is deleted");
        }

        if (player.isOnline()) {
            Object entityPlayer = PLAYER_GET_HANDLE.invoke(player);
            Object playerConnection = PLAYER_CONNECTION.invoke(entityPlayer);
            SEND_PACKET.invoke(playerConnection, packet);
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
