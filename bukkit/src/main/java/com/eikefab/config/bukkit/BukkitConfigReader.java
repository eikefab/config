package com.eikefab.config.bukkit;

import com.eikefab.config.ConfigSerializer;
import com.eikefab.config.ConfigurationReader;
import com.eikefab.config.bukkit.utils.Colorizer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BukkitConfigReader extends ConfigurationReader {

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
            object = Colorizer.apply(object.toString());
        }

        if (object instanceof List) {
            object = ((List<String>) object)
                    .stream()
                    .map(Colorizer::apply)
                    .collect(Collectors.toList());
        }

        return object;
    }

    @Override
    public Object get(String path, Class<? extends ConfigSerializer<?>> clazz) {
        try {
            final ConfigSerializer<?> serializer = clazz.newInstance();

            if (!fileConfiguration.isConfigurationSection(path)) {
                return get(path);
            }

            final ConfigurationSection section = fileConfiguration.getConfigurationSection(path);
            final Map<String, Object> item = new HashMap<>();

            for (String fieldName : serializer.fields()) {
                final Object value = section.get(fieldName);

                item.put(fieldName, value);
            }

            return serializer.deserialize(item);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return get(path);
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

}
