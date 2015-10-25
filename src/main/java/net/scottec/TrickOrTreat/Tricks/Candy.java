package net.scottec.TrickOrTreat.Tricks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by Fabian on 25.10.2015.
 */
public abstract class Candy
{
    protected String name;
    protected Material item;
    protected String[] lore;

    protected ItemStack itemStack;

    public ItemStack createCandyItem()
    {
        ItemStack is = new ItemStack(item);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);

        return is;
    }

    public ItemStack getItemStack() { return itemStack; }
    public String getName() { return name; }

    public abstract void effect(Player player);
}
