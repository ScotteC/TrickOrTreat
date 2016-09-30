package net.scottec.TrickOrTreat.Tricks;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Lightning - Trickeffect
 * The player gets struck by ligthning, blinding and stopping
 * him from moving for 10 seconds
 */

public class Lightning implements Trick {
    public void effect(Player player) {
        player.getWorld().strikeLightningEffect(player.getLocation());
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.BLINDNESS, 10 * 20, 100));
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW, 10 * 20, 100));
    }
}
