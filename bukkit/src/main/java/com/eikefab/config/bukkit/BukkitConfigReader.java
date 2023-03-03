package com.eikefab.config.bukkit;

import com.eikefab.config.ConfigurationReader;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class BukkitConfigReader extends ConfigurationReader {

    private final File file;
    private final FileConfiguration fileConfiguration;

    public BukkitConfigReader(File file) {
        Objects.requireNonNull(file);

        this.file = file;
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    @Override
    public Object get(String path) {
        Object object = fileConfiguration.get(path);

        if (object instanceof String) {
            object = colorize(object.toString());
        }

        if (object instanceof List) {
            object = ((List<String>) object)
                    .stream()
                    .map(this::colorize)
                    .collect(Collectors.toList());
        }

        return object;
    }

    @Override
    public void set(String path, Object value) {
        fileConfiguration.set(path, value);
    }

    @Override
    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
