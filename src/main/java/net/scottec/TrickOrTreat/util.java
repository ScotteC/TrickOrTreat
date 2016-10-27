package net.scottec.TrickOrTreat;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.MessageFormat;
import java.util.*;

public class util {
    public static ItemStack createItemStack(String name, Material item, List<String> lore) {
        ItemStack is = new ItemStack(item);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        is.setItemMeta(im);

        return is;
    }

    public static void dropItem(Location loc, ItemStack item, int amount) {
        item.setAmount(amount);
        getWorld().dropItemNaturally(loc, item);
    }

    public static World getWorld() {
        return Bukkit.getServer().getWorld(
                Config.getCfg().getString("common.worldname"));
    }

    public static Location getLocationFromString(String locString) {
        String[] split = locString.split(":");
        if (split.length == 5) {
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
        try {
            ResourceBundle message = ResourceBundle.getBundle("messages", locale);

            MessageFormat format = new MessageFormat("");
            format.setLocale(locale);
            format.applyPattern(ChatColor.translateAlternateColorCodes('&', message.getString(messageCode)));
            return format.format(args);
        }
        catch (MissingResourceException exp){
            exp.printStackTrace();
            return "Error";
        }
    }

    public static List<String> getStringList(String messageCode, Object... args){
        Locale locale = new Locale("en", "US");
        try {
            ResourceBundle message = ResourceBundle.getBundle("messages", locale);

            MessageFormat format = new MessageFormat("");
            format.setLocale(locale);
            format.applyPattern(ChatColor.translateAlternateColorCodes('&', message.getString(messageCode)));
            return Arrays.asList(format.format(args).split("\\|"));
        }
        catch (MissingResourceException exp) {
            exp.printStackTrace();
            return Arrays.asList("Error");
        }
    }
}
