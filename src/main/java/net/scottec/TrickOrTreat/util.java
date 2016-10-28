package net.scottec.TrickOrTreat;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.MessageFormat;
import java.util.*;

public class util {

    private static Locale locale;
    private static ResourceBundle messages;

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

    public static Locale getLocale() {
        if(locale == null) {
            String[] split = Config.getCfg().getString("common.locale").split("-");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    public static void clearRessources() {
        if(messages != null)
            messages.clearCache();
    }


    public static String getString(String messageCode, Object... args){
        try {
            if (messages == null)
                messages = ResourceBundle.getBundle("messages", getLocale());

            MessageFormat format = new MessageFormat("");
            format.setLocale(getLocale());
            format.applyPattern(ChatColor.translateAlternateColorCodes('&', messages.getString(messageCode)));
            return format.format(args);
        }
        catch (MissingResourceException exp){
            exp.printStackTrace();
            return "Error";
        }
    }

    public static List<String> getStringList(String messageCode, Object... args){
        try {
            if (messages == null)
                messages = ResourceBundle.getBundle("messages", getLocale());

            MessageFormat format = new MessageFormat("");
            format.setLocale(getLocale());
            format.applyPattern(ChatColor.translateAlternateColorCodes('&', messages.getString(messageCode)));
            return Arrays.asList(format.format(args).split("\\|"));
        }
        catch (MissingResourceException exp) {
            exp.printStackTrace();
            return Arrays.asList("Error");
        }
    }
}
