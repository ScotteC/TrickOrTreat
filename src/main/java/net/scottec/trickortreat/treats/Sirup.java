package net.scottec.trickortreat.treats;

import net.scottec.trickortreat.util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Sirup extends Treat {
    public Sirup() {
        super(util.getString("SIRUP_NAME"),
                Material.POTION,
                util.getStringList("SIRUP_LORE"));
    }

    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.CONFUSION, 15 * 20, 3), true);
    }
}
