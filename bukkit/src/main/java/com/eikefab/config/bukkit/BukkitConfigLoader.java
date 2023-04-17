package com.eikefab.config.bukkit;

import com.eikefab.config.Configuration;
import com.eikefab.config.ConfigurationLoader;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class BukkitConfigLoader extends ConfigurationLoader {

    private final Plugin plugin;

    public BukkitConfigLoader(Plugin plugin) {
        plugin.getDataFolder().mkdir();

        this.plugin = plugin;
    }

    public <T> T implement(Class<?> clazz, File file) {
        return super.implement(clazz, new BukkitConfigReader(file));
    }

    public <T> T implement(Class<?> clazz, String name) {
        final File file = new File(plugin.getDataFolder(), name);

        return implement(clazz, file);
    }

    public <T> T implement(Class<?> clazz) {
        return implement(clazz, "config.yml");
    }

}
