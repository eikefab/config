package com.eikefab.config.bungee.utils;


import net.md_5.bungee.api.ChatColor;

public final class Colorizer {

    private Colorizer() {

    }

    public static String apply(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
