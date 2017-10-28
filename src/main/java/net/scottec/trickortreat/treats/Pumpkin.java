package net.scottec.trickortreat.treats;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.scottec.trickortreat.util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Pumpkin extends Treat {

    private ProtectedRegion region;

    public Pumpkin() {
        super(util.getString("PUMPKIN_NAME"),
                Material.PUMPKIN_PIE,
                util.getStringList("PUMPKIN_LORE"));
        // ToDo: Ugly hardcode...
        try {
            this.region = WGBukkit.getRegionManager(Bukkit.getWorld("world"))
                    .getRegion("hw17pvplabyrinth");
            if (this.region != null)
                System.out.println("[TrickOrTreat] Pumpkin-Region loaded");
            else
                System.out.println("[TrickOrTreat] Pumpkin-Region not found...");
        } catch (NullPointerException exp) {
            System.out.println("[TrickOrTreat] Pumpkin-Region not defined...");
            this.region = null;
        }
    }

    public void effect(Player player) {
        // ToDo: Ugly hardcode...
        if(this.region != null && this.region.contains(
                new Vector(player.getLocation().getX(),
                        player.getLocation().getY(),
                        player.getLocation().getZ()))) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Hihi, hier gibts keinen Jump-Boost!");
        }
        else
            player.addPotionEffect(new PotionEffect(
                PotionEffectType.JUMP, 10 * 20, 5), true);
    }
}
