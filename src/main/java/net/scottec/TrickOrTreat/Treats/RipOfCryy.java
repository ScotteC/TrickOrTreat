package net.scottec.TrickOrTreat.Treats;

import net.scottec.TrickOrTreat.util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RipOfCryy extends Treat {
    public RipOfCryy() {
        super(util.getString("RIPPOFCRYY_NAME"),
                Material.COOKED_MUTTON,
                util.getStringList("RIPPOFCRYY_LORE"));
    }

    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.INCREASE_DAMAGE, 10 * 20, 2), true);
    }
}
