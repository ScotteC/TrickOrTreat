package net.scottec.TrickOrTreat.Tricks;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Explosion - Trickeffect
 * Plays an explosioneffect at the players location, confuse
 * and stops him from moving for 10seconds
 */

public class Explosion implements Trick {

    public void effect(Player player) {
        player.getWorld().playEffect(
                player.getLocation(),Effect.EXPLOSION_HUGE, 10);
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.CONFUSION, 10 * 20, 100));
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW, 10 * 20, 5));
    }
}
