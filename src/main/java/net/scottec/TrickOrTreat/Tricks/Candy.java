package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Fabian on 25.10.2015.
 */
public abstract class Candy
{
    protected String name;
    protected Material item;
    protected List<String> lore;

    protected ItemStack itemStack;

    public ItemStack createCandyItem()
    {
        return util.createItemStack(name, item, lore);
    }

    public ItemStack getItemStack() { return itemStack; }
    public String getName() { return name; }

    public abstract void effect(Player player);
}
