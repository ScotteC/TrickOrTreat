package net.scottec.TrickOrTreat.Treats;

import net.scottec.TrickOrTreat.util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BrainStew extends Treat {
    public BrainStew() {
        super(util.getString("BRAINSTEW_NAME"),
                Material.COOKED_RABBIT,
                util.getStringList("BRAINSTEW_LORE"));
    }

    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.NIGHT_VISION, 10 * 20, 5), true);
    }
}
