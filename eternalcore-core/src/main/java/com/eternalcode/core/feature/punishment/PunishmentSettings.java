package com.eternalcode.core.feature.punishment;

import org.bukkit.Material;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface PunishmentSettings {

    boolean blockUsingSignOnMute();

    boolean messageWhenBanned();

    Duration messageWhenBannedCooldown();

    boolean reasonLegthEnabled();

    int minReasonLength();

    int maxReasonLength();

    String permanentLabel();

    List<String> banKickScreen();

    List<String> banIpKickScreen();

    List<String> kickScreen();

    int historyPageSize();

    Map<Integer, String> warnEscalations();

    int historyGuiEntryUpdateIntervalTicks();

    int historyGuiPageSize();

    int historyGuiFetchBatchSize();

    String historyGuiTitle();

    String historyGuiPlayerTitle();

    Material historyGuiFillerMaterial();

    Material historyGuiBackArrowMaterial();

    String historyGuiBackArrowName();

    Material historyGuiForwardArrowMaterial();

    String historyGuiForwardArrowName();

    String historyGuiEntryName();

    List<String> historyGuiEntryLore();

    Map<String, Material> historyGuiEntryMaterials();

    Material historyGuiEntryDefaultMaterial();
}
