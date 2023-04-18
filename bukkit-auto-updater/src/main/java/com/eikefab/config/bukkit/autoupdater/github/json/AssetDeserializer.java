package com.eikefab.config.bukkit.autoupdater.github.json;

import com.eikefab.config.bukkit.autoupdater.github.Asset;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AssetDeserializer implements JsonDeserializer<Asset> {

    @Override
    public Asset deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject object = json.getAsJsonObject();

        return null;
    }

}
