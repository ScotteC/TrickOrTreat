package net.scottec.TrickOrTreat.Treats;

import net.scottec.TrickOrTreat.util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BakedApple extends Treat {
    public BakedApple() {
        super(util.getString("APPLE_NAME"),
                Material.APPLE,
                util.getStringList("APPLE_LORE"));
    }

    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY, 10 * 20, 1));
    }
}
