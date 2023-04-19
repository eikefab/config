package com.eikefab.config.bukkit.utils;

import org.bukkit.ChatColor;

public final class Colorizer {

    private Colorizer() {

    }

    public static String apply(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
