package com.eternalcode.core.feature.catboy;

import org.bukkit.entity.Cat;

import java.util.UUID;

public record Catboy(UUID uuid, Cat.Type selectedType) {
}
