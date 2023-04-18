package com.eikefab.config.bukkit.autoupdater.github.json;

import com.eikefab.config.bukkit.autoupdater.github.Release;
import com.eikefab.config.bukkit.autoupdater.github.Repository;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class RepositoryDeserializer implements JsonDeserializer<Repository> {

    @Override
    public Repository deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final List<Release> releases = context.deserialize(json.getAsJsonObject().get("releases"), Release.class);

        return new Repository(releases);
    }

}
