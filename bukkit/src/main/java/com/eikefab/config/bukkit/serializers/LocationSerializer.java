package com.eikefab.config.bukkit.serializers;

import static java.lang.Double.parseDouble;

import com.eikefab.config.ConfigSerializer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Map;

public class LocationSerializer extends ConfigSerializer<Location> {
    @Override
    public List<String> fields() {
        return ImmutableList.of(
                "world",
                "x",
                "y",
                "z"
        );
    }

    @Override
    public Map<String, Object> serialize(Location item) {
        return ImmutableMap.of(
                "world", item.getWorld() == null ? Bukkit.getWorlds().get(0).getName() : item.getWorld().getName(),
                "x", item.getX(),
                "y", item.getY(),
                "z", item.getZ()
        );
    }

    @Override
    public Location deserialize(Map<String, Object> item) {
        final World bukkitWorld = Bukkit.getWorld(item.get("world").toString());
        final double x = parseDouble(item.get("x").toString());
        final double y = parseDouble(item.get("y").toString());
        final double z = parseDouble(item.get("z").toString());

        return new Location(bukkitWorld, x, y, z);
    }
}
