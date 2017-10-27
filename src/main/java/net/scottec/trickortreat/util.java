package net.scottec.trickortreat;

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
        loc.getWorld().dropItemNaturally(loc, item);
    }

    public static Location getLocationFromString(String locString) {
        String[] split = locString.split(":");
        try {
            return new Location(Bukkit.getWorld(split[0]),
                    Double.parseDouble(split[1]),
                    Double.parseDouble(split[2]),
                    Double.parseDouble(split[3]),
                    Float.parseFloat(split[4]),
                    Float.parseFloat(split[5]));
        } catch (Exception exp) {
            System.out.println("[TrickOrTreat] Not a valid LocationString: "  + locString);
        }
        return null;
    }

    public static String getStringFromLocation(Location loc) {
        return String.format(
                loc.getWorld().getName() + ":%.2f:%.2f:%.2f:%.2f:%.2f",
                loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
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
