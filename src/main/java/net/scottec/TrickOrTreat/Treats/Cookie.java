package net.scottec.TrickOrTreat.Treats;

import net.scottec.TrickOrTreat.util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cookie extends Treat {
    public Cookie() {
        super(util.getString("COOKIE_NAME"),
                Material.COOKIE,
                util.getStringList("COOKIE_LORE"));
    }

    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SPEED, 10 * 20, 5), true);
    }
}
