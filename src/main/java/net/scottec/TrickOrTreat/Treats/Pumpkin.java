package net.scottec.TrickOrTreat.Treats;

import net.scottec.TrickOrTreat.util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Pumpkin extends Treat {
    public Pumpkin() {
        super(util.getString("PUMPKIN_NAME"),
                Material.PUMPKIN_PIE,
                util.getStringList("PUMPKIN_LORE"));
    }

    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.JUMP, 10 * 20, 5), true);
    }
}
