package net.scottec.TrickOrTreat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
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

    public static void giveHalloweenstick(Player player)
    {
        ItemStack halloweenstick = createItemStack(
                Config.getTxt().getString("halloweenstick.name"),
                Material.BLAZE_ROD,
                Config.getTxt().getStringList("halloweenstick.lore"));

        if (!(player.getInventory().containsAtLeast(halloweenstick, 1)))
            player.getInventory().addItem(halloweenstick);
    }

    public static Location getLocationFromString(String locString)
    {
        String[] split = locString.split(":");
        if(split.length == 5)
        {
            return new Location(util.getWorld(),
                    Double.parseDouble(split[0]),
                    Double.parseDouble(split[1]),
                    Double.parseDouble(split[2]),
                    Float.parseFloat(split[3]),
                    Float.parseFloat(split[4]));
        }
        return null;
    }

    public static String getString(String messageCode, Object... args){
        Locale locale = new Locale("en", "US");
        ResourceBundle message = ResourceBundle.getBundle("messages");

        MessageFormat format = new MessageFormat("");
        format.setLocale(locale);
        format.applyPattern(message.getString(messageCode));
        return format.format(args);
    }

    public static List<String> getStringList(String messageCode, Object... args){
        Locale locale = new Locale("en", "US");
        ResourceBundle message = ResourceBundle.getBundle("messages");

        MessageFormat format = new MessageFormat("");
        format.setLocale(locale);
        format.applyPattern(message.getString(messageCode));
        return Arrays.asList(format.format(args).split("\\|"));
    }
}
