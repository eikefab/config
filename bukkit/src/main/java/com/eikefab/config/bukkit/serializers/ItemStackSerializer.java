package com.eikefab.config.bukkit.serializers;

import com.eikefab.config.ConfigSerializer;
import com.eikefab.config.bukkit.utils.Colorizer;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class ItemStackSerializer extends ConfigSerializer<ItemStack> {

    @Override
    public List<String> fields() {
        return ImmutableList.of(
                "material",
                "amount",
                "display-name",
                "lore",
                "enchantments"
        );
    }

    @Override
    public Map<String, Object> serialize(ItemStack item) {
        final ItemMeta itemMeta = item.getItemMeta();

        final String lore =
                item.hasItemMeta() && itemMeta.hasLore() ?
                itemMeta.getLore().stream().collect(Collectors.joining(",")) :
                "";

        final Map<String, String> enchantments = new HashMap<>();

        for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
            enchantments.put(entry.getKey().getName(), entry.getValue().toString());
        }

        return ImmutableMap.of(
                "material", item.getType().name(),
                "amount", item.getAmount(),
                "display-name", item.hasItemMeta() ? itemMeta.getDisplayName() : "",
                "lore", lore,
                "enchantments", Joiner.on(",").withKeyValueSeparator("=").join(enchantments)
        );
    }

    @Override
    public ItemStack deserialize(Map<String, Object> item) {
        final Material material = Material.getMaterial(item.get("material").toString());
        final int amount = Integer.parseInt(item.get("amount").toString());
        final String displayName = Colorizer.apply(item.get("display-name").toString());
        final List<String> lore = Arrays.stream(item.get("lore").toString().split(","))
                .map(Colorizer::apply)
                .collect(Collectors.toList());
        final Map<String, String> enchantments = Splitter.on(',')
                .withKeyValueSeparator('=')
                .split(item.get("enchantments").toString());

        final ItemStack itemStack = new ItemStack(material, amount);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        for (Map.Entry<String, String> entry : enchantments.entrySet()) {
            itemStack.addUnsafeEnchantment(Enchantment.getByName(entry.getKey()), Integer.parseInt(entry.getValue()));
        }

        if (!displayName.isEmpty()) {
            itemMeta.setDisplayName(displayName);
        }

        if (!lore.isEmpty()) {
            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
