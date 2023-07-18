package com.eikefab.config.bukkit.autoupdater.github;

import java.io.File;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

public class Release {

    private final String id;
    private final String name;
    private final String author;
    private final String tagName;
    private final boolean preRelease;
    private final LinkedList<Asset> assets;

    public Release(String id, String name, String author, String tagName, boolean preRelease, LinkedList<Asset> assets) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.tagName = tagName;
        this.preRelease = preRelease;
        this.assets = assets;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getTagName() {
        return tagName;
    }

    public boolean isPreRelease() {
        return preRelease;
    }

    public LinkedList<Asset> getAssets() {
        return assets;
    }

    public Optional<Asset> filter(Predicate<Asset> predicate) {
        return assets.stream().filter(predicate).findFirst();
    }

    public File download(File folder, Predicate<Asset> predicate, String token) {
        final Optional<Asset> target = filter(predicate);

        final File actualFolder = new File(folder, "release-" + tagName);

        if (!folder.exists()) {
            folder.mkdir();
        }

        if (!actualFolder.exists()) {
            actualFolder.mkdir();
        }

        return target.map(asset -> asset.download(folder, token)).orElse(null);
    }

    public File download(File folder, String assetName) {
        return download(folder, (asset) -> asset.getName().equals(assetName), null);
    }

}
