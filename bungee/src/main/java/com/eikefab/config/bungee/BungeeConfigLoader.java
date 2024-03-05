package com.eikefab.config.bungee;

import com.eikefab.config.ConfigurationLoader;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

public class BungeeConfigLoader extends ConfigurationLoader {

    private final Plugin plugin;

    public BungeeConfigLoader(Plugin plugin) {
        this.plugin = plugin;

        plugin.getDataFolder().mkdir();
    }

    public <T> T implement(Class<?> clazz, File file) {
        try {
            return super.implement(clazz, new BungeeConfigReader(file, plugin));
        } catch (Exception exception) {
            exception.printStackTrace();

            return null;
        }
    }

    public <T> T implement(Class<?> clazz, String name) {
        final File file = new File(plugin.getDataFolder(), name.endsWith(".yml") ? name : name + ".yml");

        return implement(clazz, file);
    }

    public <T> T implement(Class<?> clazz) {
        return implement(clazz, "config.yml");
    }

}
