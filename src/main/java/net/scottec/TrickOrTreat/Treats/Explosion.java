package net.scottec.TrickOrTreat.Treats;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 25.10.2015.
 */
public class Explosion implements Treatment
{

    public void effect(Player player)
    {
        player.getWorld().playEffect(player.getLocation(),
                Effect.EXPLOSION_HUGE,
                10);
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.CONFUSION, 10 * 20, 100));
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW, 10*20, 5));
    }
}
