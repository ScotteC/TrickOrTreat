package net.scottec.TrickOrTreat.Tricks;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Flames - Trickeffect
 * Set the player on flames for 10 seconds, but doesnt burn him
 */

public class Flames implements Trick {
    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.FIRE_RESISTANCE, 10 * 20, 10));
        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 0);
        player.setFireTicks(10 * 20);
    }
}
