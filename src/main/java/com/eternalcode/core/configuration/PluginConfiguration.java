/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Description;

import java.io.Serializable;

public final class PluginConfiguration implements Serializable {
    @Description("# Useful Assets")
    public String someValue = "some value";
}
