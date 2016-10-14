package net.scottec.TrickOrTreat.Treats;

import net.scottec.TrickOrTreat.util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Treat {
    private String name;
    private ItemStack item;

    public Treat(String name, Material material, List<String> lore) {
        this.name = name;
        this.item = util.createItemStack(this.name, material, lore);
    }

    public ItemStack getItem() {
        return this.item;
    }

    public String getName() {
        return this.name;
    }

    public abstract void effect(Player player);
}
