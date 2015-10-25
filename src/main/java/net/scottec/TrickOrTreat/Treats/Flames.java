package net.scottec.TrickOrTreat.Treats;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 25.10.2015.
 */
public class Flames extends Treatment
{
    public void effect(Player player)
    {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.FIRE_RESISTANCE, 10*20, 10));
        player.setFireTicks(10*20);
    }
}
