package net.scottec.TrickOrTreat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Fabian on 29.10.2015.
 */
public class util
{
    public static ItemStack createItemStack(String name, Material item, List<String> lore)
    {
        ItemStack is = new ItemStack(item);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        is.setItemMeta(im);

        return is;
    }

    public static void dropItem(Location loc, ItemStack item, int amount)
    {
        item.setAmount(amount);
        getWorld().dropItemNaturally(loc, item);
    }

    public static World getWorld()
    {
        return Bukkit.getServer().getWorld(
                Config.getCfg().getString("common.worldname"));
    }
}
