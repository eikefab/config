package com.eikefab.config.bukkit.autoupdater;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

import com.eikefab.config.bukkit.autoupdater.github.Repository;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

public class ConfigUpdater {

    private final Plugin plugin;
    private final File folder;
    private final File[] files;

    public ConfigUpdater(Plugin plugin, File folder, File... files) {
        this.plugin = plugin;
        this.folder = folder;
        this.files = files;

        plugin.getDataFolder().mkdir();
        folder.mkdir();
    }

    public ConfigUpdater(Plugin plugin, File... files) {
        this(plugin, new File(plugin.getDataFolder(), "config-updates"), files);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public File[] getFiles() {
        return files;
    }

    public void update(String repository, String token, boolean info) {
        final Repository repo = Repository.query(repository, token);
        final Logger logger = plugin.getLogger();

        if (repo == null) {
            if (info) {
                logger.severe("Couldn't connect to GitHub repository " + repository + ", aborting autoupdate...");
            }

            return;
        }

        for (File file : getFiles()) {
            final String name = file.getName();

            if (!name.endsWith(".yml")) {
                continue;
            }

            final File asset = repo.getLatestRelease().download(folder, name);

            if (asset == null) {
                continue;
            }

            final FileConfiguration config = loadConfiguration(file);
            final FileConfiguration assetConfig = loadConfiguration(asset);

            final int currentVersion = config.getInt("file-version", 1);
            final int assetVersion = assetConfig.getInt("asset-version", 1);

            if (assetVersion > currentVersion) {
                if (info) {
                    logger.info(String.format(
                            "%s is outdated! %s > %s, updating...",
                            name,
                            assetVersion,
                            currentVersion
                    ));
                }

                for (String key : assetConfig.getKeys(true)) {
                    config.set(key, assetConfig.get(key));
                }

                try {
                    config.save(file);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (info) {
                logger.info(name + " is updated, skipping...");
            }
        }
    }

    public void update(String repository, boolean info) {
        update(repository, null, info);
    }

}
