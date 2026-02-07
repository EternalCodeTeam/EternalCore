package com.eternalcode.core.feature.catboy;

import org.bukkit.entity.Cat;

import java.util.UUID;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record Catboy(UUID uuid, Cat.Type selectedType) {
}
