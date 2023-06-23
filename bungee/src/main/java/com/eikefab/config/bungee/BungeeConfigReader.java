package com.eikefab.config.bungee;

import com.eikefab.config.ConfigSerializer;
import com.eikefab.config.ConfigurationReader;
import com.eikefab.config.bungee.utils.Colorizer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BungeeConfigReader extends ConfigurationReader {

    private static final ConfigurationProvider PROVIDER = ConfigurationProvider.getProvider(YamlConfiguration.class);

    private final Plugin plugin;
    private final File file;
    private final Configuration configuration;

    public BungeeConfigReader(File file, Plugin plugin) throws IOException {
        this.file = file;
        this.plugin = plugin;

        load();

        this.configuration = PROVIDER.load(file);
    }

    private void load() {
        final File folder = file.getParentFile();

        if (!folder.exists()) {
            folder.mkdir();
        }

        if (!file.exists()) {
            try (InputStream inputStream = plugin.getResourceAsStream(file.getName())) {
                Files.copy(inputStream, file.toPath());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public Object get(String path) {
        Object object = configuration.get(path);

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

            if (!(configuration.get(path) instanceof Configuration)) {
                return get(path);
            }

            final Configuration section = configuration.getSection(path);
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
        configuration.set(path, value);
    }

    @Override
    public void save() {}

}
