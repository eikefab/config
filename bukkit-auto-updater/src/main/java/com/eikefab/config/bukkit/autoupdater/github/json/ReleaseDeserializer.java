package com.eikefab.config.bukkit.autoupdater.github.json;

import com.eikefab.config.bukkit.autoupdater.github.Asset;
import com.eikefab.config.bukkit.autoupdater.github.Release;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ReleaseDeserializer implements JsonDeserializer<Release> {

    @Override
    public Release deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject object = json.getAsJsonObject();

        final String id = object.get("id").getAsString();
        final String name = object.get("name").getAsString();
        final String author = object.get("author.login").getAsString();
        final String tagName = object.get("tag_name").getAsString();
        final boolean preRelease = object.get("prerelease").getAsBoolean();

        final LinkedList<Asset> assets =
                object.getAsJsonArray("assets")
                      .asList()
                      .stream()
                      .map((element) -> context.<Asset>deserialize(element, Asset.class))
                      .collect(Collectors.toCollection(LinkedList::new));

        return new Release(id, name, author, tagName, preRelease, assets);
    }

}
